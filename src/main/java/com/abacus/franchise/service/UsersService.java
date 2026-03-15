package com.abacus.franchise.service;

import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import com.abacus.franchise.response.SuccessResponse;
import com.abacus.franchise.viewModels.AssignExamRequst;
import com.abacus.franchise.viewModels.AuthRequest;
import com.abacus.franchise.viewModels.KitRequest;
import com.abacus.franchise.viewModels.QuestionsAnswerRequest;
import com.abacus.franchise.viewModels.SubmitExamRequest;
import com.abacus.franchise.viewModels.SwitchCourseRequest;
import com.abacus.franchise.viewModels.UserViewModel;
import com.abacus.franchise.viewModels.ViewProductRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface UsersService {

    public SuccessResponse saveOrUpdateUsers(UserViewModel userViewModel, MultipartFile profileImage, MultipartFile documentImage, HttpServletRequest request);

    public SuccessResponse loginUsers(AuthRequest authRequest);

    public SuccessResponse getUsersById(UUID userId, String roleName);

 //Franchise   
    public SuccessResponse getStudentByFranchiseId(UUID franchiseId);

    public SuccessResponse getAllCoursesByFranchiseId(UUID franchiseId);

    public SuccessResponse sendCourseKitRequest(KitRequest kitRequest);
    
    public SuccessResponse getAllProductDetail();
    
    public SuccessResponse sendProductRequest(UUID franchiseId,List<ViewProductRequest> productRequests);

    public SuccessResponse getAllExamDetailByCourseId(String courseId);
    
    public SuccessResponse studentAssignExam(AssignExamRequst assignExamRequst);

    public SuccessResponse getFinalPaperByCourseId(String courseId);
    
    public SuccessResponse getPracticePaperByCourseId(String courseId);

    public SuccessResponse getAllOfflineExamStudentsByCourseId(String courseId,String franchiseId);
    
    public SuccessResponse getAllOnlineExamResultStudentsByCourseId(String courseId,String franchiseId);

    public SuccessResponse getAllCompleteCoursesStudentByFranchiseId(String franchiseId);

    public SuccessResponse changeCoursesByStudentOrFranchiseId(SwitchCourseRequest courseRequest);

    public SuccessResponse getAllProductRequest(String franchiseId);

    public SuccessResponse getAllKitRequest(String franchiseId);
    
    public SuccessResponse getAllUnAssignStudentByExamId(String examId);
    
    public SuccessResponse requestToReAssignExam(AssignExamRequst assignExamRequst);

//Student
    
    public SuccessResponse getAllCourseExamByStudent(String studentId);
    
    public SuccessResponse getAllQuestionByStudent(String studentId);

    public SuccessResponse submitExam(SubmitExamRequest examRequest);

   


}
