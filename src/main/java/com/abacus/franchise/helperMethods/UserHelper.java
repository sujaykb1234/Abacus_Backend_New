package com.abacus.franchise.helperMethods;

import java.util.List;

import com.abacus.franchise.model.Users;
import com.abacus.franchise.utility.ImageStoreProcess;
import com.abacus.franchise.viewModels.UserViewModel;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UserHelper {

    public boolean isValidUser(UserViewModel v) {
        return v != null &&
                v.getEmail() != null &&
                v.getMobile() != null &&
                v.getFirstName() != null &&
                v.getLastName() != null &&
                v.getPasswordHash() != null &&
                v.getRoleId() != null &&
                v.getDateOfBirth() != null;
    }

    public boolean isValidAddress(UserViewModel v) {
        return v != null &&
                v.getLine1() != null &&
                v.getLandmark() != null &&
                v.getCity() != null &&
                v.getStateId() != null &&
                v.getDistrictId() != null &&
                v.getPincode() != null;
    }

    public void handleFiles(
            Users users,
            MultipartFile profile,
            MultipartFile document,
            HttpServletRequest request) {

        if (profile != null && !profile.isEmpty()) {
            List<String> result = ImageStoreProcess.saveFile(profile, request);
            if (result != null && result.size() >= 2) {
                users.setProfileName(result.get(0));
                users.setProfileLink(result.get(1));
            }
        }

        if (document != null && !document.isEmpty()) {
            List<String> result = ImageStoreProcess.saveFile(document, request);
            if (result != null && result.size() >= 2) {
                users.setDocumentName(result.get(0));
                users.setDocumentLink(result.get(1));
            }
        }
    }
}
