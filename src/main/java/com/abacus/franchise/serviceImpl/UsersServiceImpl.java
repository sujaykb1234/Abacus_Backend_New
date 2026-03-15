package com.abacus.franchise.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.abacus.franchise.dto.BasicUserDetail;
import com.abacus.franchise.dto.CourseDetail;
import com.abacus.franchise.dto.CourseKitDTO;
import com.abacus.franchise.dto.CredentialDetail;
import com.abacus.franchise.dto.ExamDetail;
import com.abacus.franchise.dto.ExamDetailProjection;
import com.abacus.franchise.dto.KitRequestAddressDTO;
import com.abacus.franchise.dto.KitRequestsDetail;
import com.abacus.franchise.dto.ProductDetail;
import com.abacus.franchise.dto.ProductRequestDetail;
import com.abacus.franchise.dto.QuestionDTO;
import com.abacus.franchise.dto.QuestionProjection;
import com.abacus.franchise.dto.StudentCourseDetail;
import com.abacus.franchise.dto.StudentCourseExamProjection;
import com.abacus.franchise.dto.StudentDetails;
import com.abacus.franchise.dto.UserAddressDetail;
import com.abacus.franchise.dto.UserDetail;
import com.abacus.franchise.enums.ExamMode;
import com.abacus.franchise.enums.ExamStatus;
import com.abacus.franchise.enums.Roles;
import com.abacus.franchise.exception.ResourceNotFoundException;
import com.abacus.franchise.model.Address;
import com.abacus.franchise.model.AssignExam;
import com.abacus.franchise.model.AssignExamStudent;
import com.abacus.franchise.model.KitOrderItem;
import com.abacus.franchise.model.KitRequests;
import com.abacus.franchise.model.ProductRequest;
import com.abacus.franchise.model.StudentAnswer;
import com.abacus.franchise.model.StudentCourse;
import com.abacus.franchise.model.TokenDetail;
import com.abacus.franchise.model.Users;
import com.abacus.franchise.repo.AddressRepository;
import com.abacus.franchise.repo.AssignExamRepository;
import com.abacus.franchise.repo.AssignExamStudentRepository;
import com.abacus.franchise.repo.CourseRepository;
import com.abacus.franchise.repo.DistrictRepository;
import com.abacus.franchise.repo.ExamRepository;
import com.abacus.franchise.repo.FranchiseCourseRepository;
import com.abacus.franchise.repo.KitOrderItemRepository;
import com.abacus.franchise.repo.KitRequestsRepository;
import com.abacus.franchise.repo.ProductRepository;
import com.abacus.franchise.repo.ProductRequestRepository;
import com.abacus.franchise.repo.QuestionRepository;
import com.abacus.franchise.repo.RolesRepository;
import com.abacus.franchise.repo.StateRepository;
import com.abacus.franchise.repo.StudentAnswerRepository;
import com.abacus.franchise.repo.StudentCourseRepository;
import com.abacus.franchise.repo.TokenDetailRepo;
import com.abacus.franchise.repo.UsersRepository;
import com.abacus.franchise.response.SuccessResponse;
import com.abacus.franchise.security.JwtUtil;
import com.abacus.franchise.service.UsersService;
import com.abacus.franchise.utility.ImageStoreProcess;
import com.abacus.franchise.viewModels.AssignExamRequst;
import com.abacus.franchise.viewModels.AuthRequest;
import com.abacus.franchise.viewModels.KitRequest;
import com.abacus.franchise.viewModels.QuestionsAnswerRequest;
import com.abacus.franchise.viewModels.SubmitExamRequest;
import com.abacus.franchise.viewModels.SwitchCourseRequest;
import com.abacus.franchise.viewModels.UserViewModel;
import com.abacus.franchise.viewModels.ViewProductRequest;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	RolesRepository rolesRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	DistrictRepository districtRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	TokenDetailRepo tokenDetailRepo;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	KitOrderItemRepository kitOrderItemRepository;

	@Autowired
	KitRequestsRepository kitRequestsRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductRequestRepository productRequestRepository;
	
	@Autowired
	ExamRepository examRepository;
	
	@Autowired
	AssignExamRepository assignExamRepository;
	
	@Autowired
	AssignExamStudentRepository assignExamStudentRepository;

	@Autowired
	StudentCourseRepository studentCourseRepository;
	
	@Autowired
	FranchiseCourseRepository franchiseCourseRepository;
	
	@Autowired
	StudentAnswerRepository studentAnswerRepository;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Override
	public SuccessResponse saveOrUpdateUsers(
			UserViewModel viewUser,
			MultipartFile profileImage,
			MultipartFile documentImage,
			HttpServletRequest request) {

		SuccessResponse response = new SuccessResponse();

		/* ================= BASIC VALIDATION ================= */

		if (viewUser == null
				|| viewUser.getEmail() == null
				|| viewUser.getMobile() == null
				|| viewUser.getFirstName() == null
				|| viewUser.getLastName() == null
				|| viewUser.getDateOfBirth() == null
				|| viewUser.getRoleId() == null) {
			response.basicDetailsIsNull();
			return response;
		}

		if (viewUser.getLine1() == null
				|| viewUser.getCity() == null
				|| viewUser.getDistrictId() == null
				|| viewUser.getStateId() == null
				|| viewUser.getPincode() == null) {
			response.addressDetailIsNull();
			return response;
		}

		/* ================= ROLE VALIDATION ================= */

		String roleName = rolesRepository.checkRoleIdPresentOrNot(viewUser.getRoleId().toString());
		if (roleName == null) {
			response.rolesNotFound();
			return response;
		}
		// roleName already like ROLE_ADMIN / ROLE_FRANCHISE (from DB)

		/* ================= STATE / DISTRICT ================= */

		if (stateRepository.checkStateIdPresentOrNot(viewUser.getStateId().toString()) == 0) {
			response.stateNotFound();
			return response;
		}

		if (districtRepository.checkDistrictIdPresentOrNot(viewUser.getDistrictId().toString()) == 0) {
			response.districtNotFound();
			return response;
		}

		/* ================= SAVE vs UPDATE ================= */

		boolean isSave = (viewUser.getUserId() == null);

		UUID mobileOwnerId = usersRepository.checkMobileNoIsExistOrNot(viewUser.getMobile());
		if (isSave) {
			if (mobileOwnerId != null) {
				response.mobileAlreadyExist();
				return response;
			}
		} else {
			if (mobileOwnerId != null && !mobileOwnerId.equals(viewUser.getUserId())) {
				response.mobileAlreadyExist();
				return response;
			}
		}

		UUID emailOwnerId = usersRepository.checkEmailIsExistOrNot(viewUser.getEmail());
		if (isSave) {
			if (emailOwnerId != null) {
				response.emailAlreadyExist();
				return response;
			}
		} else {
			if (emailOwnerId != null && !emailOwnerId.equals(viewUser.getUserId())) {
				response.emailAlreadyExist();
				return response;
			}
		}

		/* ================= USER ENTITY ================= */

		Users users;
		if (isSave) {
			if (viewUser.getPasswordHash() == null) {
				response.passwordRequired();
				return response;
			}
			users = new Users();
		} else {
			users = usersRepository.findById(viewUser.getUserId())
					.orElseThrow(() -> new RuntimeException("User not found"));
		}

		users.setFirstName(viewUser.getFirstName());
		users.setMiddleName(viewUser.getMiddleName());
		users.setLastName(viewUser.getLastName());
		users.setEmail(viewUser.getEmail());
		users.setMobile(viewUser.getMobile());
		users.setRoleId(viewUser.getRoleId());
		users.setDateOfBirth(viewUser.getDateOfBirth());

		if (viewUser.getPasswordHash() != null) {
			users.setPasswordHash(passwordEncoder.encode(viewUser.getPasswordHash()));
		}

		if (viewUser.getFranchiseId() != null) {
			UUID franchiseId = usersRepository
					.checkUserIsExistOrNotByIdOrStatus(viewUser.getFranchiseId().toString(),List.of("MASTER_FRANCHISE","FRANCHISE"));
			if (franchiseId != null) {
				users.setFranchiseId(franchiseId);
			}			
		}

		users.setFranchiseName(viewUser.getFranchiseName());

		if (profileImage != null) {
			List<String> saved = ImageStoreProcess.saveFile(profileImage, request);
			if (saved != null) {
				users.setProfileName(saved.get(0));
				users.setProfileLink(saved.get(1));
			}
		}

		if (documentImage != null) {
			List<String> saved = ImageStoreProcess.saveFile(documentImage, request);
			if (saved != null) {
				users.setDocumentName(saved.get(0));
				users.setDocumentLink(saved.get(1));
			}
		}
		
		if(viewUser.getCourseId() != null && viewUser.getFranchiseId() != null) {
			
			UUID checkCourseIdIsExistOrNot = franchiseCourseRepository.checkCourseExistOrNotInFranchise(viewUser.getCourseId().toString(),viewUser.getFranchiseId().toString());
					
			if (checkCourseIdIsExistOrNot == null) {
				response.courseNotFoundInFranchise();
				return response;
			}
		}

		Users savedUser = usersRepository.save(users);
		
		/* ================= STUDENT COURSE ================= */
		if(viewUser.getCourseId() != null && viewUser.getFranchiseId() != null) {
			StudentCourse studentCourse = new StudentCourse();
			studentCourse.setCourseId(viewUser.getCourseId());
			studentCourse.setFranchiseId(viewUser.getFranchiseId());
			studentCourse.setStudentId(savedUser.getUserId());
			studentCourseRepository.save(studentCourse);
		 }
		
		/* ================= ADDRESS ================= */

		Address address = addressRepository.findByUserId(savedUser.getUserId())
				.orElse(new Address());

		address.setUserId(savedUser.getUserId());
		address.setLine1(viewUser.getLine1());
		address.setLandmark(viewUser.getLandmark());
		address.setCity(viewUser.getCity());
		address.setStateId(viewUser.getStateId());
		address.setDistrictId(viewUser.getDistrictId());
		address.setPincode(viewUser.getPincode());

		addressRepository.save(address);

		/* ================= JWT ================= */

		String accessToken = jwtUtil.generateAccessToken(
				savedUser.getMobile(),
				roleName,
				savedUser.getUserId()
				);

		TokenDetail token = new TokenDetail();
		token.setUserId(savedUser.getUserId());
		token.setAccessTokenHash(accessToken);
		tokenDetailRepo.save(token);

		response.saveUserResponse(accessToken);
		return response;
	}

	@Override
	public SuccessResponse loginUsers(AuthRequest authRequest) {

	    SuccessResponse response = new SuccessResponse();

	    if (authRequest.getUsername() == null || authRequest.getUsername().isBlank()
	            || authRequest.getPassword() == null || authRequest.getPassword().isBlank()) {

	        response.loginCredentialIsNull();
	        return response;
	    }

	    CredentialDetail user =
	            usersRepository.checkMobileNoIsPresentOrNot(authRequest.getUsername(),authRequest.getRolename(),authRequest.getUserId().toString());

	    if (user == null) {
	        response.usernameIncorrect();
	        return response;
	    }

	    if (!user.getIsActive()) {
	        response.userIsDeactivate();
	        return response;
	    }

	    if (!passwordEncoder.matches(authRequest.getPassword(), user.getPasswordHash())) {
	        response.wrongPassword();
	        return response;
	    }


	    response.loginSuccessfully(null);

	    return response;
	}

	@Override
	public SuccessResponse getUsersById(UUID userId, String roleName) {

	    SuccessResponse response = new SuccessResponse();

	    UserAddressDetail userDetail = usersRepository
	            .getUserAddressDetailByUserId(userId.toString(), List.of(roleName));

	    if (userDetail == null) {
	        throw new ResourceNotFoundException(roleName + " NOT FOUND");
	    }

	    if ("STUDENT".equals(roleName)) {

	        UserAddressDetail franchiseDetail = usersRepository
	                .getUserAddressDetailByUserId(
	                        userDetail.getFranchiseId(),
	                        List.of("MASTER_FRANCHISE", "FRANCHISE"));

	        StudentDetails studentDetails = new StudentDetails();
	        studentDetails.setStudentDetail(userDetail);
	        studentDetails.setFranchiseDetail(franchiseDetail);

	        response.userFoundResponse(studentDetails, roleName);
	        return response;
	    }

	    response.userFoundResponse(userDetail, roleName);
	    return response;
	}
	@Override
	public SuccessResponse getStudentByFranchiseId(UUID franchiseId) {
		SuccessResponse response = new SuccessResponse();

		List<UserAddressDetail> studentDetailByFranchiseId = usersRepository
				.getStudentDetailByFranchiseId(franchiseId.toString());

		if (studentDetailByFranchiseId != null && !studentDetailByFranchiseId.isEmpty()) {
			response.userFoundResponse(studentDetailByFranchiseId,"STUDENT");
			return response;
		}else {
	        throw new ResourceNotFoundException("STUDENT NOT FOUND");
		}
	}

	@Override
	public SuccessResponse getAllCoursesByFranchiseId(UUID franchiseId) {

		   List<CourseDetail> allCoursesByFranchiseId =
		            courseRepository.getAllCoursesByFranchiseId(franchiseId.toString());

		    if (allCoursesByFranchiseId == null || allCoursesByFranchiseId.isEmpty()) {
		        throw new ResourceNotFoundException("COURSE NOT FOUND");
		    }

		    SuccessResponse response = new SuccessResponse();
		    response.courseFound(allCoursesByFranchiseId);

		    return response;

	}

	@Override
	public SuccessResponse sendCourseKitRequest(KitRequest kitRequest) {
		SuccessResponse response = new SuccessResponse();

		KitRequests kitRequests = new KitRequests();

		if (kitRequest.getFranchiseId() != null) {
			UUID checkFranchiseIdIsExistOrNot = usersRepository
					.checkUserIsExistOrNotByIdOrStatus(kitRequest.getFranchiseId().toString(),List.of("MASTER_FRANCHISE","FRANCHISE"));

			if (checkFranchiseIdIsExistOrNot == null) {
				response.franchiesnotfound();
				return response;
			}

			kitRequests.setFranchiseId(checkFranchiseIdIsExistOrNot);
		}
		
		if (kitRequest.getAddressId() != null) {
			UUID addressById = addressRepository.getAddressById(kitRequest.getAddressId().toString());

			if (addressById == null) {
				response.addressNotFound();
				return response;
			}

			kitRequests.setAddressId(addressById);

		} else {
			if (kitRequest.getStateId() == null
					|| stateRepository.checkStateIdPresentOrNot(kitRequest.getStateId().toString()) == 0) {
				response.stateNotFound();
				return response;
			}

			if (kitRequest.getDistrictId() == null
					|| districtRepository.checkDistrictIdPresentOrNot(kitRequest.getDistrictId().toString()) == 0) {
				response.districtNotFound();
				return response;
			}

			Address address = new Address();
			address.setUserId(kitRequests.getFranchiseId());
			address.setLine1(kitRequest.getLine1());
			address.setLandmark(kitRequest.getLandmark());
			address.setCity(kitRequest.getCity());
			address.setStateId(kitRequest.getStateId());
			address.setDistrictId(kitRequest.getDistrictId());
			address.setPincode(kitRequest.getPincode());

			Address saveAddress = addressRepository.save(address);
			kitRequests.setAddressId(saveAddress.getAddressId());

		}
		
		kitRequests.setCreatedBy(kitRequest.getFranchiseId());
		kitRequests.setUpdatedBy(kitRequest.getFranchiseId());
		KitRequests save_kitrequest = kitRequestsRepository.save(kitRequests);

		if (kitRequest.getKitOrderItems() != null) {

			for (KitOrderItem item : kitRequest.getKitOrderItems()) {
				UUID checkCourseIdIsExistOrNot = courseRepository
						.checkCourseIdIsExistOrNot(item.getCourseId().toString());

				if (checkCourseIdIsExistOrNot == null) {
					response.courseNotFound(item.getCourseId().toString());
					return response;
				}

				item.setKitRequestId(save_kitrequest.getKitRequestId());
				item.setCreatedBy(kitRequest.getFranchiseId());
				item.setUpdatedBy(kitRequest.getFranchiseId());
			}
			kitOrderItemRepository.saveAll(kitRequest.getKitOrderItems());
		}

		response.kitsSentSuccessfully();
		return response;
	}

	@Override
	public SuccessResponse getAllProductDetail() {
		
		SuccessResponse response = new SuccessResponse();
		
		List<ProductDetail> allProductDetail = productRepository.getAllProductDetail();
		
		if(allProductDetail != null) {
			response.productFound(allProductDetail);
			return response;
		}
		
		response.productNotFound("");
		return response;
	}

	@SuppressWarnings("unused")
	@Override
	public SuccessResponse sendProductRequest(UUID franchiseId, List<ViewProductRequest> productRequests) {
		SuccessResponse response =  new SuccessResponse();
		
		List<ProductRequest> productRequests2 = new ArrayList<>();
		
		
		if (franchiseId != null) {
			UUID checkFranchiseIdIsExistOrNot = usersRepository
					.checkUserIsExistOrNotByIdOrStatus(franchiseId.toString(),List.of("MASTER_FRANCHISE","FRANCHISE"));

			if (checkFranchiseIdIsExistOrNot == null) {
				response.franchiesnotfound();
				return response;
			}

		}
		
		
		for(ViewProductRequest viewProductRequest : productRequests) {
			
			ProductRequest productRequest = new ProductRequest();
			productRequest.setFranchiseId(franchiseId);
			
			ProductDetail productIdById = productRepository.getProductIdById(viewProductRequest.getProductId().toString());
			
			if(productIdById.getQuantity() < viewProductRequest.getQuantity()) {
				response.quantityIsLessThan();
				return response;
			}
			
			if(productIdById == null) {
				response.productNotFound("");
				return response;
			}
			
			
			productRequest.setProductId(UUID.fromString(productIdById.getProductId()));
			productRequest.setQuantity(viewProductRequest.getQuantity());
			
			productRequest.setCreatedBy(franchiseId);
			productRequest.setUpdatedBy(franchiseId);
			productRequests2.add(productRequest);
		
			int remainQty = productIdById.getQuantity() - viewProductRequest.getQuantity();
			productRepository.updateProductQuantityById(productIdById.getProductId(),remainQty);
		}
		
		productRequestRepository.saveAll(productRequests2);
		
		
		response.sendProductOrder();
		return response;
		
		
	}

	@Override
	public SuccessResponse getAllExamDetailByCourseId(String courseId) {
		
		SuccessResponse response = new SuccessResponse();
		
		UUID checkCourseIdIsExistOrNot = courseRepository
				.checkCourseIdIsExistOrNot(courseId);

		if (checkCourseIdIsExistOrNot == null) {
			response.courseNotFound("");
			return response;
		}
		
		List<ExamDetail> allExamDetailByCourseId = examRepository.getAllExamDetailByCourseId(courseId);
		
		if(allExamDetailByCourseId == null || allExamDetailByCourseId.isEmpty()) {
			response.examNotFound();
			return response;
		}
		
		response.examFound(allExamDetailByCourseId);
		return response;
		
	}

	@Override
	public SuccessResponse studentAssignExam(AssignExamRequst assignExamRequst) {
		SuccessResponse response = new SuccessResponse();
		
		if (assignExamRequst.getFranchiseId() != null) {
			UUID checkFranchiseIdIsExistOrNot = usersRepository
					.checkUserIsExistOrNotByIdOrStatus(assignExamRequst.getFranchiseId().toString(),List.of("MASTER_FRANCHISE","FRANCHISE"));

			if (checkFranchiseIdIsExistOrNot == null) {
				response.franchiesnotfound();
				return response;
			}

		}
		
		if(assignExamRequst.getStudentId() != null) {
			for(UUID studentId : assignExamRequst.getStudentId()) {
				if (assignExamRequst != null) {
					UUID checkFranchiseIdIsExistOrNot = usersRepository
							.checkUserIsExistOrNotByIdOrStatus(studentId.toString(),List.of("STUDENT"));
		
					if (checkFranchiseIdIsExistOrNot == null) {
						response.studentNotFound();
						return response;
					}
		
				}
			}
		}else {
			response.studentIdIsNull();
			return response;
		}
		
		if(assignExamRequst.getExamId().toString() == null) {
			response.examIdIsNull();
			return response;
		}
		
		UUID checkExamIdIsPresentOrNot = examRepository.checkExamIdIsPresentOrNot(assignExamRequst.getExamId().toString());
		
		if(checkExamIdIsPresentOrNot == null) {
			response.examNotFound();
			return response; 
		}
		
		
		AssignExam assignExam = new AssignExam();
		assignExam.setExamMode(assignExamRequst.getExamMode());
		assignExam.setExamId(checkExamIdIsPresentOrNot);
		assignExam.setExamTime(assignExamRequst.getExamTime());
		assignExam.setFranchiseId(assignExamRequst.getFranchiseId());

		assignExam.setCreatedBy(assignExamRequst.getFranchiseId());
		assignExam.setUpdatedBy(assignExamRequst.getFranchiseId());
		AssignExam saveAssignExam = assignExamRepository.save(assignExam);
		
		for(UUID studentId : assignExamRequst.getStudentId()) {
            AssignExamStudent assignExamStudent = new AssignExamStudent();
			assignExamStudent.setAssignExamId(saveAssignExam.getAssignExamId());
			assignExamStudent.setStudentId(studentId);
			assignExamStudent.setCreatedBy(assignExamRequst.getFranchiseId());
			assignExamStudent.setUpdatedBy(assignExamRequst.getFranchiseId());
			assignExamStudentRepository.save(assignExamStudent);
		}		
	
	    response.assignExamSuccessfully();    	
		return response;
	}

	@Override
	public SuccessResponse getFinalPaperByCourseId(String courseId) {
		SuccessResponse response = new SuccessResponse();
		
		UUID checkCourseIdIsExistOrNot = courseRepository
				.checkCourseIdIsExistOrNot(courseId);

		if (checkCourseIdIsExistOrNot == null) {
			response.courseNotFound("");
			return response;
		}
		
		
		List<String> offlineExamPdfByCourseId =
		        Optional.ofNullable(examRepository.getFinalPaperByCourseId(courseId))
		                .orElse(Collections.emptyList())
		                .stream()
		                .filter(Objects::nonNull)
		                .toList();
		
		if(offlineExamPdfByCourseId.isEmpty()) {
			response.examNotFound();
			return response;
		}
		
		response.examFound(offlineExamPdfByCourseId);
		return response;	
		
	}
	
	public SuccessResponse getPracticePaperByCourseId(String courseId) {
		SuccessResponse response = new SuccessResponse();
		
		UUID checkCourseIdIsExistOrNot = courseRepository
				.checkCourseIdIsExistOrNot(courseId);

		if (checkCourseIdIsExistOrNot == null) {
			response.courseNotFound("");
			return response;
		}
		
		
		List<String> offlineExamPdfByCourseId =
		        Optional.ofNullable(examRepository.getPracticePaperByCourseId(courseId))
		                .orElse(Collections.emptyList())
		                .stream()
		                .filter(Objects::nonNull)
		                .toList();
		
		if(offlineExamPdfByCourseId.isEmpty()) {
			response.examNotFound();
			return response;
		}
		
		response.examFound(offlineExamPdfByCourseId);
		return response;	
		
	}

	@Override
	public SuccessResponse getAllOfflineExamStudentsByCourseId(String courseId,String franchiseId) {
		SuccessResponse response = new SuccessResponse();
		
		UUID checkCourseIdIsExistOrNot = courseRepository
				.checkCourseIdIsExistOrNot(courseId);

		if (checkCourseIdIsExistOrNot == null) {
			response.courseNotFound("");
			return response;
		}
		
		List<BasicUserDetail> allOfflineExamStudentsByCourseId = assignExamRepository.getAllExamStudentsByCourseId(franchiseId,courseId,ExamStatus.ASSIGNED.toString(),ExamMode.OFFLINE.toString());
		
		if(allOfflineExamStudentsByCourseId == null || allOfflineExamStudentsByCourseId.isEmpty()) {
			response.studentNotFound();
			return response;
		}
				
		
		response.studentFound(allOfflineExamStudentsByCourseId);
		return response;
	}
	
	@Override
	public SuccessResponse getAllOnlineExamResultStudentsByCourseId(String courseId,String franchiseId) {
		SuccessResponse response = new SuccessResponse();
		
		UUID checkCourseIdIsExistOrNot = courseRepository
				.checkCourseIdIsExistOrNot(courseId);

		if (checkCourseIdIsExistOrNot == null) {
			response.courseNotFound("");
			return response;
		}
		
		List<BasicUserDetail> allOnlineExamStudentsByCourseId = assignExamRepository.getAllExamStudentsByCourseId(franchiseId,courseId,ExamStatus.COMPLETED.toString(),ExamMode.ONLINE.toString());
		
		if(allOnlineExamStudentsByCourseId == null || allOnlineExamStudentsByCourseId.isEmpty()) {
			response.studentNotFound();
			return response;
		}
				
		
		response.studentFound(allOnlineExamStudentsByCourseId);
		return response;
	}
	
	
	@Override
	public SuccessResponse getAllCompleteCoursesStudentByFranchiseId(String franchiseId) {

		SuccessResponse response = new SuccessResponse();
		
		if (franchiseId != null) {
			UUID checkFranchiseIdIsExistOrNot = usersRepository
					.checkUserIsExistOrNotByIdOrStatus(franchiseId,List.of("MASTER_FRANCHISE","FRANCHISE"));

			if (checkFranchiseIdIsExistOrNot == null) {
				response.franchiesnotfound();
				return response;
			}
		}
		
		List<StudentCourseDetail> allCoursesByFranchiseId = courseRepository
				.getAllCompleteCoursesStudentByFranchiseId(franchiseId.toString(),ExamStatus.COMPLETED.toString());

		if (allCoursesByFranchiseId.isEmpty()) {
			response.studentNotFound();
			return response;
		}

		response.studentFound(allCoursesByFranchiseId);
		return response;

	}

	@Override
	public SuccessResponse changeCoursesByStudentOrFranchiseId(SwitchCourseRequest courseRequest) {
		SuccessResponse response = new SuccessResponse();
		
         StudentCourse studentCourse = new StudentCourse();
		
		if (courseRequest.getFranchiseId() != null) {
			UUID checkFranchiseIdIsExistOrNot = usersRepository
					.checkUserIsExistOrNotByIdOrStatus(courseRequest.getFranchiseId(),List.of("MASTER_FRANCHISE","FRANCHISE"));

			if (checkFranchiseIdIsExistOrNot == null) {
				response.franchiesnotfound();
				return response;
			}

			studentCourse.setFranchiseId(checkFranchiseIdIsExistOrNot);
		}
		
		if (courseRequest.getStudentId() != null) {
			UUID checkStudentIdIsExistOrNot = usersRepository
					.checkUserIsExistOrNotByIdOrStatus(courseRequest.getStudentId(),List.of("STUDENT"));

			if (checkStudentIdIsExistOrNot == null) {
				response.studentNotFound();
				return response;
			}
            
			studentCourse.setStudentId(checkStudentIdIsExistOrNot);
		}
		
		
		if(courseRequest.getCourseId() != null) {
			UUID checkCourseIdIsExistOrNot = courseRepository
					.checkCourseIdIsExistOrNot(courseRequest.getCourseId());
	
			if (checkCourseIdIsExistOrNot == null) {
				response.courseNotFound(null);
				return response;
			}
			
			studentCourse.setCourseId(checkCourseIdIsExistOrNot);
		}
		
		UUID checkCourseAlreadyExistOrNot = studentCourseRepository.checkCourseAlreadyExistOrNot(courseRequest.getCourseId(),courseRequest.getFranchiseId(), courseRequest.getStudentId());
		
		if(checkCourseAlreadyExistOrNot != null) {
			response.courseAlreadyExists();
			return response;
		}
		
		UUID checkExamCompltedOrNot = assignExamRepository.checkExamCompltedOrNot(courseRequest.getFranchiseId(), courseRequest.getStudentId());
		
		if(checkExamCompltedOrNot == null) {
			response.examNotComplete();
			return response;
		}
		
		
		studentCourseRepository.courseDisActiveByFranchiseAndStudentId(courseRequest.getFranchiseId(), courseRequest.getStudentId());
		
		studentCourse.setCreatedBy(UUID.fromString(courseRequest.getFranchiseId()));
		studentCourse.setUpdatedBy(UUID.fromString(courseRequest.getFranchiseId()));
		studentCourseRepository.save(studentCourse);
		
		
        
		
		response.switchCourseSuccessfully();
		return response;
	}

	@Override
	public SuccessResponse getAllProductRequest(String franchiseId) {
		SuccessResponse response = new SuccessResponse();
		
		List<ProductRequestDetail> allProductRequest = productRequestRepository.getAllProductRequest(franchiseId);
		
		if(allProductRequest.isEmpty()) {
			response.requestNotFound();
			return response;
		}
		
		response.requestFound(allProductRequest);
		return response;
	}

	@Override
	public SuccessResponse getAllKitRequest(String franchiseId) {
		SuccessResponse response = new SuccessResponse();
		
        List<KitRequestsDetail> allKitRequestsDetailByFranchiseId = kitRequestsRepository.getAllKitRequestsDetailByFranchiseId(franchiseId);
        
		if(allKitRequestsDetailByFranchiseId.isEmpty()) {
			response.requestNotFound();
			return response;
		}
		
		Map<String, KitRequestAddressDTO> map = new HashMap<>();

		for (KitRequestsDetail r : allKitRequestsDetailByFranchiseId) {

		    String key = r.getKitRequestId();

		    KitRequestAddressDTO dto = map.computeIfAbsent(key, k -> {
		        KitRequestAddressDTO d = new KitRequestAddressDTO();
		        d.setPlacedDate(r.getPlacedDate());
		        d.setDispatchedDate(r.getDispatchedDate());
		        d.setDeliveredDate(r.getDeliveredDate());

		        d.setLine1(r.getLine1());
		        d.setLandmark(r.getLandmark());
		        d.setCity(r.getCity());
		        d.setDistrictName(r.getDistrictName());
		        d.setStateName(r.getStateName());
		        d.setPincode(r.getPincode());
		        d.setCountryName(r.getCountryName());

		        d.setRequestStatus(r.getRequestStatus());
		        d.setCourses(new ArrayList<>());
		        return d;
		    });

		    dto.getCourses().add(
		        new CourseKitDTO(r.getCourseName(), r.getKitCount())
		    );
		}
		
	    response.requestFound(new ArrayList<>(map.values()));
		return response;
	}

	@Override
	public SuccessResponse getAllUnAssignStudentByExamId(String examId) {
		 
		SuccessResponse response = new SuccessResponse();
		
		UUID checkExamIdIsPresentOrNot = examRepository.checkExamIdIsPresentOrNot(examId);
		
		if(checkExamIdIsPresentOrNot == null) {
			response.examNotFound();
			return response; 
		}
		
		List<UserDetail> allUnAssignStudentByExamId = usersRepository.getAllUnAssignStudentByExamId(examId,Roles.STUDENT.toString());
		
		if(allUnAssignStudentByExamId.isEmpty()) {
			response.studentNotFound();
			return response;
		}
		
		response.studentFound(allUnAssignStudentByExamId);
		return response;
	}

	@Override
	public SuccessResponse requestToReAssignExam(AssignExamRequst assignExamRequst) {
		SuccessResponse response = new SuccessResponse();
		
		if(assignExamRequst.getStudentId() != null) {
			for(UUID studentId : assignExamRequst.getStudentId()) {
				if (assignExamRequst != null) {
					UUID checkFranchiseIdIsExistOrNot = usersRepository
							.checkUserIsExistOrNotByIdOrStatus(studentId.toString(),List.of("STUDENT"));
		
					if (checkFranchiseIdIsExistOrNot == null) {
						response.studentNotFound();
						return response;
					}
		
				}
			}
		}else {
			response.studentIdIsNull();
			return response;
		}
		
		if(assignExamRequst.getExamId().toString() == null) {
			response.examIdIsNull();
			return response;
		}
		
		UUID checkExamIdIsPresentOrNot = examRepository.checkExamIdIsPresentOrNot(assignExamRequst.getExamId().toString());
		
		if(checkExamIdIsPresentOrNot == null) {
			response.examNotFound();
			return response; 
		}
		
		UUID assignExamIdByExamAndStudentId = examRepository.getAssignExamIdByExamAndStudentId(assignExamRequst.getExamId().toString(),assignExamRequst.getStudentId().get(0).toString());
		
		if(checkExamIdIsPresentOrNot == null) {
			response.assignExamNotFound();
			return response; 
		}		
		
		int changeExamStatusByExamId = assignExamRepository.changeExamStatusByExamId(assignExamIdByExamAndStudentId.toString());
		
		if(changeExamStatusByExamId == 0) {
			response.reassignFailed();
			return response;
		}
		
		response.reassignSuccessfully();
		return response;	
	 }

//Student
	
	@Override
	public SuccessResponse getAllCourseExamByStudent(String studentId) {
		SuccessResponse response = new SuccessResponse();
		
		UUID checkFranchiseIdIsExistOrNot = usersRepository
				.checkUserIsExistOrNotByIdOrStatus(studentId.toString(),List.of("STUDENT"));

		if (checkFranchiseIdIsExistOrNot == null) {
			response.studentNotFound();
			return response;
		}
		
		List<StudentCourseExamProjection> studentCourseExamDetails = studentCourseRepository.getStudentCourseExamDetails(studentId);
		
		if(studentCourseExamDetails.isEmpty()) {
			response.courseNotFound("");
			return response;
		}
		
		response.courseFound(studentCourseExamDetails);
		return response;
	}

	@Override
	public SuccessResponse getAllQuestionByStudent(String studentId) {
		SuccessResponse response = new SuccessResponse();

		UUID checkFranchiseIdIsExistOrNot = usersRepository
				.checkUserIsExistOrNotByIdOrStatus(studentId.toString(),List.of("STUDENT"));

		if (checkFranchiseIdIsExistOrNot == null) {
			response.studentNotFound();
			return response;
		}
		
		ExamDetailProjection examDetails = examRepository.getExamDetails(studentId);
		List<QuestionProjection> questions = examRepository.getExamQuestions(studentId);
		
		if(examDetails == null && questions == null) {
			response.questionNotFound();
			return response;
		}
		
		QuestionDTO dto = new QuestionDTO(examDetails, questions);
		response.questionFound(dto);
		return response;
	}

	@Override
	public SuccessResponse submitExam(SubmitExamRequest examRequest ) {
	
		SuccessResponse response = new SuccessResponse();
		
		List<StudentAnswer> answers =new ArrayList<>();
		
		int countMarks = 0;
		
		
		for(QuestionsAnswerRequest request: examRequest.getQuestionsAnswerRequest()) {
			Integer examAttemptByQuestionIdAndStudentId = studentAnswerRepository.getExamAttemptByQuestionIdAndStudentId(examRequest.getStudentId().toString(),request.getQuestionId().toString());
			
			StudentAnswer answer = new StudentAnswer();

			if(examAttemptByQuestionIdAndStudentId == null) {
				answer.setExamAttempt(1);
			}else {
				answer.setExamAttempt(examAttemptByQuestionIdAndStudentId+1);
			}
			
			answer.setAssignExamId(examRequest.getAssignExamId());	
            answer.setQuestionId(request.getQuestionId());
			answer.setChosenAnswer(request.getChosenAnswer());			
			answer.setStudentId(examRequest.getStudentId());
			String correctAnswerByQuestionId = questionRepository.getCorrectAnswerByQuestionId(request.getQuestionId().toString());
		    
			if(correctAnswerByQuestionId.equalsIgnoreCase(request.getChosenAnswer())) {
				answer.setIsCorrect(true);
				countMarks++;
			}
			
			answers.add(answer);
		
		}
		
		List<StudentAnswer> saveAll = studentAnswerRepository.saveAll(answers);
		int updateMarksByExamId = assignExamRepository.updateMarksByExamId(countMarks,examRequest.getAssignExamId().toString(),examRequest.getStartTime(),examRequest.getEndTime());
		
		if(saveAll.isEmpty() && updateMarksByExamId == 0) {
			response.examNotSend();
			return response;
		}
		
		ExamResultDTO examResultDTO = new ExamResultDTO(examRequest.getQuestionsAnswerRequest().size(), countMarks, countMarks);
			
		
		response.examSendSuccessfully(examResultDTO);
		return response;
		
	}
}
