package com.unionbankng.future.learn.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import com.unionbankng.future.learn.entities.*;
import com.unionbankng.future.learn.enums.LectureType;
import com.unionbankng.future.learn.pojo.*;
import com.unionbankng.future.learn.repositories.*;
import com.unionbankng.future.learn.util.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.Response;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CourseBulkUploadService {

    Logger logger = LoggerFactory.getLogger(CourseBulkUploadService.class);
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final CourseContentRepository courseContentRepository;
    private final LectureRepository lectureRepository;
    private final QuestionOptionRepository questionOptionRepository;
    private final QuestionRepository questionRepository;
    private final UtilityServiceInterfaceService utilityServiceInterfaceService;


    public String getCell(Cell cell) {
        if (cell.getCellType().name().equals("NUMERIC"))
            return String.valueOf(cell.getNumericCellValue());
        else if (cell.getCellType().name().equals("BOOLEAN"))
            return String.valueOf(cell.getBooleanCellValue());
        else
            return cell.getStringCellValue();
    }

    public APIResponse<String> saveFile(String url, BlobType blobType) {
        try {
            logger.info("Uploading File ... " + url);
            String ext = url.substring(url.lastIndexOf("."));
            if (ext != null) {

                UUID uuid = UUID.randomUUID();
                String uniqueness = uuid.toString();
                String fileName = uniqueness + ext;
                logger.info("Uploading..."+url);
                Response<APIResponse<String>> response = utilityServiceInterfaceService.uploadMediaFromURL(url, fileName, blobType.toString());

                if (response.isSuccessful()) {
                    return new APIResponse("success", true, response.body().getPayload());
                }
                else
                {
                    logger.info(response.errorBody().toString());
                    logger.info(String.valueOf(response.isSuccessful()));
                    return new APIResponse(response.errorBody().string(), false, null);
                }

            } else {
                return new APIResponse("Cover image URL not supported", false, null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse("Error occurred", false, null);
        }
    }

    public StreamingLocatorResponse saveStreamingVideo(String url) {
        try {
            logger.info("Uploading Video ... " + url);
            Response<APIResponse<StreamingLocatorResponse>> response = utilityServiceInterfaceService.uploadVideoStreamFromURL(url);
            //generate streaming like and asset name
            if (response.isSuccessful())
                return response.body().getPayload();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info(ex.getMessage());
            return null;
        }
    }

    public APIResponse saveCourseInformation(JwtUserDetail jwtUserDetail, Sheet courseSheet) {
        try {
            Stream<Row> streamRow = StreamSupport.stream(courseSheet.spliterator(), false);
            //create instructors
            List<Instructor> instructors = new ArrayList<>();
            Instructor existingInstructor = instructorRepository.findByInstructorUUID(jwtUserDetail.getUserUUID()).orElse(null);
            if (existingInstructor != null) {
                existingInstructor.setNumberOfCourses(existingInstructor.getNumberOfCourses() + 1);
                instructorRepository.save(existingInstructor);
                instructors.add(existingInstructor);
            } else {
                Instructor instructor = new Instructor();
                instructor.setId(jwtUserDetail.getUserId());
                instructor.setInstructorUUID(jwtUserDetail.getUserUUID());
                instructor.setNumberOfCourses(1);
                instructorRepository.save(instructor);
                instructors.add(instructor);
            }
            //create course information
            Course course = new Course();
            course.setCreatorUUID(jwtUserDetail.getUserUUID());
            course.setInstructors(instructors);

            if (courseSheet.getLastRowNum() == 0 && courseSheet.getRow(0) == null) {
                return new APIResponse("No Course information found", false, null);

            } else {
                streamRow.forEach(row -> {
                    Stream<Cell> cellStream = StreamSupport.stream(row.spliterator(), false);
                    List<String> cellVal = cellStream.map((cell) -> {
                        String columnId = CellReference.convertNumToColString(cell.getColumnIndex());
                        String rowId = String.valueOf(cell.getRowIndex());
                        String val = getCell(cell);
                        return columnId + rowId + "=>" + val;
                    }).collect(Collectors.toList());
                    List<String> B = Arrays.asList(cellVal.get(1).split("=>"));
                    List<String> D = Arrays.asList(cellVal.get(3).split("=>"));
                    logger.info(B.toString());
                    logger.info(D.toString());

                    switch (B.get(0)) {
                        case "B4":
                            course.setCourseTitle(B.get(1));
                            break;
                        case "B6":
                            course.setShortDesc(B.get(1));
                            break;
                        case "B8":
                            course.setRequirements(B.get(1));
                            break;
                        case "B10":
                            logger.info("No Category found at the entity");
                            break;
                        case "B12":
                            course.setPrice(BigDecimal.valueOf(Double.parseDouble(B.get(1))));
                            break;
                    }
                    switch (D.get(0)) {
                        case "D4":
                            course.setEstimatedTimeToComplete(D.get(1));
                            break;
                        case "D6":
                            course.setDescription(D.get(1));
                            break;
                        case "D8":
                            course.setOutcomes(D.get(1));
                            break;
                        case "D10":
                            course.setIsPaid(D.get(1) == "Paid" ? true : false);
                            break;
                        case "D12":
                            String url=D.get(1);
                            APIResponse<String> response = this.saveFile(url,  BlobType.IMAGE);
                            logger.info("Upload response");
                            logger.info(response.getMessage());
                            if (response.isSuccess())
                                course.setCourseImg(response.getPayload());
                            break;
                    }
                });

                course.setIsPublished(Boolean.TRUE);
                logger.info(course.getCourseImg() + " ...URL saved");
                if (course.getCourseImg() != null) {
                    Course savedCourse = courseRepository.save(course);
                    return new APIResponse("success", true, savedCourse);
                } else {
                    return new APIResponse("Unable to upload Cover Image", false, null);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(ex.getMessage(), false, null);
        }
    }

    public APIResponse saveCourseContents(JwtUserDetail jwtUserDetail, Course course, Sheet contentSheet) {
        try {
            List<Lecture> lectures = new ArrayList<>();
            Row headerRow = contentSheet.getRow(0);

            if (contentSheet.getLastRowNum() == 1 && contentSheet.getRow(1) == null) {
                logger.info("No course content found");
                return new APIResponse("No course content found", false, null);
            } else {

                for (Row row : contentSheet) {
                    if (row.getRowNum() > 0) {
                        ArrayList<Question> questionsBase = new ArrayList<>();
                        ArrayList<QuestionOption> optionsBase = new ArrayList<>();
                        Lecture lecture = new Lecture();
                        Question question = new Question();
                        CourseContent content = new CourseContent();

                        if (getCell(row.getCell(3)).equals("Video")) {
                            for (Cell cell : row) {
                                String value = getCell(cell);
                                String columnId = CellReference.convertNumToColString(cell.getColumnIndex());
                                switch (columnId) {
                                    case "C":
                                        lecture.setTitle(value);
                                        content.setCourseContentText(value);
                                        content.setCourseId(course.getId());
                                        content.setCreatorUUID(course.getCreatorUUID());
                                        content.setIndexNo(row.getRowNum());
                                        break;
                                    case "D":
                                        lecture.setType(LectureType.VIDEO);
                                        break;
                                    case "E":
                                        logger.info("Preparing video for upload: " + value);
                                        StreamingLocatorResponse response = this.saveStreamingVideo(value);
                                        logger.info(response.getAssetName());
                                        if (response != null) {
                                            lecture.setStreamingLocatorName(response.getLocatorName());
                                            lecture.setOutputAssetName(response.getAssetName());
                                        } else {
                                            logger.info("Unable to Upload Video");
                                        }
                                        break;
                                }
                            }
                        } else if (getCell(row.getCell(3)).equals("Quiz")) {
                            lecture.setType(LectureType.QUIZ);
                            for (Cell cell : row) {
                                String value = getCell(cell);
                                String columnId = CellReference.convertNumToColString(cell.getColumnIndex());
                                QuestionOption option = new QuestionOption();
                                switch (columnId) {
                                    case "F":
                                        question.setQuestionText(value);
                                        question.setIndexNo(row.getRowNum());
                                        break;
                                    case "G":
                                        option.setIndexNo(1);
                                        option.setOptionText(value);
                                        optionsBase.add(questionOptionRepository.save(option));

                                        break;
                                    case "H":
                                        option.setOptionText(value);
                                        option.setIndexNo(2);
                                        optionsBase.add(questionOptionRepository.save(option));
                                        break;
                                    case "I":
                                        option.setOptionText(value);
                                        option.setIndexNo(3);
                                        optionsBase.add(questionOptionRepository.save(option));
                                        break;
                                    case "J":
                                        option.setOptionText(value);
                                        option.setIndexNo(4);
                                        optionsBase.add(questionOptionRepository.save(option));
                                        break;
                                    case "K":
                                        option.setOptionText(value);
                                        option.setIndexNo(5);
                                        optionsBase.add(questionOptionRepository.save(option));
                                        break;
                                    case "L":
                                        String correctAnswer = getCell(row.getCell(11));
                                        if (getCell(headerRow.getCell(6)).equals(correctAnswer))
                                            question.setAnswerIndex(1);
                                        if (getCell(headerRow.getCell(7)).equals(correctAnswer))
                                            question.setAnswerIndex(2);
                                        if (getCell(headerRow.getCell(8)).equals(correctAnswer))
                                            question.setAnswerIndex(3);
                                        if (getCell(headerRow.getCell(9)).equals(correctAnswer))
                                            question.setAnswerIndex(4);
                                        if (getCell(headerRow.getCell(10)).equals(correctAnswer))
                                            question.setAnswerIndex(5);
                                        break;
                                }
                            }

                        }

                        lecture.setIndexNo(row.getRowNum());
                        lecture.setCreatorUUID(jwtUserDetail.getUserUUID());
                        lecture.setCourseId(course.getId());
                        lecture.setCourseContentId(course.getId());

                        //save course content details if its Video
                        if (getCell(row.getCell(3)).equals("Video")) {
                            if (lecture.getStreamingLocatorName() != null) {
                                CourseContent savedContent = courseContentRepository.save(content);
                                if (savedContent != null)
                                    lecture.setCourseContentId(savedContent.getId());

                                //save lecture if its video
                                Lecture savedLecture = lectureRepository.save(lecture);
                                if (savedLecture != null)
                                    lectures.add(savedLecture);

                            } else {
                                return new APIResponse("Unable to Upload Video of Row " + row.getRowNum(), false, null);

                            }
                        } else {
                            // set questions if its quiz
                            if (getCell(row.getCell(3)).equals("Quiz")) {
                                lecture.setQuestions(questionsBase);
                            }
                            //save lecture
                            Lecture savedLecture = lectureRepository.save(lecture);
                            if (savedLecture != null)
                                lectures.add(savedLecture);

                            if (savedLecture != null && getCell(row.getCell(3)).equals("Quiz")) {
                                question.setLectureId(savedLecture.getId());
                                question.setOptions(optionsBase);
                                questionsBase.add(question);
                                logger.info(new ObjectMapper().writeValueAsString(question));
                                questionRepository.save(question);
                            }
                        }
                    }
                }
            }
            return new APIResponse("success", true, lectures);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(ex.getMessage(), false, null);
        }

    }


    public APIResponse uploadBulkCourseFiles(Principal principal, MultipartFile courseFile) throws IOException {
        try {
            JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
            Path tempDir = Files.createTempDirectory("");
            File tmpFile = tempDir.resolve(courseFile.getOriginalFilename()).toFile();
            if (tmpFile.length() > 0) {
                logger.info("The selected file is empty");
                return  new APIResponse("The selected file is empty",false,null);
            } else {
                courseFile.transferTo(tmpFile);
                Workbook workbook = WorkbookFactory.create(tmpFile);
                Sheet courseSheet = workbook.getSheetAt(0);
                Sheet contentSheet = workbook.getSheetAt(2);
                APIResponse response=this.saveCourseInformation(jwtUserDetail,courseSheet);
                if(response.isSuccess()) {
                    //course content
                    Course course= (Course) response.getPayload();
                    APIResponse savedCourseResponse=  this.saveCourseContents(jwtUserDetail, course, contentSheet);
                    return  savedCourseResponse;
                }else{
                    return  response;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return  new APIResponse(ex.getMessage(),false,null);
        }
    }
}
