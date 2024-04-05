package com.example.easypay.services.serviceimpl;

import com.example.easypay.modals.dtos.sellerDto.SellerDto;
import com.example.easypay.modals.projections.SellerDetailsProjection;
import com.example.easypay.modals.dtos.shared.NewPasswordDto;
import com.example.easypay.modals.dtos.shared.ResetPasswordDto;

import com.example.easypay.modals.entities.seller.ContactDetail;
import com.example.easypay.modals.entities.seller.Seller;
import com.example.easypay.modals.entities.seller.SellerRole;
import com.example.easypay.modals.enums.Verification;

import com.example.easypay.repository.seller.SellerRepository;
import com.example.easypay.repository.seller.SellerRoleRepository;
import com.example.easypay.services.interfaces.SellerRoleService;
import com.example.easypay.services.interfaces.SellerService;
import com.example.easypay.utils.exceptionUtil.ApiException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SellerServiceImpl implements SellerService {

    private final SellerRoleRepository SellerRoleRespository;

    private final SellerRepository sellerRepository;
    private final SellerRoleService sellerRoleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public Boolean isSellerPresent(String email)
    {
        return sellerRepository.existsByEmail(email);
    }
    @Override
    public String register(SellerDto sellerDto) {

        String email=sellerDto.getEmail();
        Boolean isSellerPresent=isSellerPresent(email);

        if(isSellerPresent)
        {
            throw new ApiException("Seller with this email already exists");
        }
        else
        {
            Seller seller=new Seller();
            HashSet<SellerRole> roles=new HashSet<>();
            Set<String> sellerRoles=sellerDto.getRoles();

            sellerRoles.forEach(sellerRole -> {
                SellerRole role = sellerRoleService.getSellerRoleByRoleName(sellerRole);
                roles.add(role);
            });
            seller.setCompanyName(sellerDto.getCompanyName());
            seller.setEmail(email);
            seller.setPassword(passwordEncoder.encode(sellerDto.getPassword()));
            seller.setRoles(roles);

            seller.setVerificationStatus(Verification.UNVERIFIED);
            //Set<ContactDetail> contacts=new HashSet<>();
            if (sellerDto.getContactDetails() != null) {
                //ContactDetail contactDetails = this.modelMapper.map(sellerDto.getContactDetails(), ContactDetail.class);

                sellerDto.getContactDetails().forEach(contact->{
                    ContactDetail contactDetails = this.modelMapper.map(contact, ContactDetail.class);
                   contactDetails.addSeller(seller);
                    seller.addContact(contactDetails);
                    //contacts.add(contactDetails);
                });
            }

            sellerRepository.save(seller);
            log.info(seller.toString());
            return seller.getSellerToken();
        }
    }
    @Override
    public String getSellerToken(String email) {
        return sellerRepository.findTokenByEmail(email);
    }

    @Override
    public String login(HttpServletResponse httpServletResponse) {
        return "";
    }

    @Override
    public SellerDetailsProjection getSellerDetails(String token) {


        SellerDetailsProjection sellerDetails= sellerRepository.getSellerDetails(token);
        log.info("sellerDetails="+(sellerDetails==null));
        return sellerDetails;
    }

    @Override
    public void sendVerificationEmail() {

    }

    @Override
    public void verifyEmail() {

    }

    @Override
    public void sendOtp() {

    }

    @Override
    public void verifyOtp() {

    }

    @Override
    public String sendPasswordResetOTP(String email, Long phone) {
        return null;
    }

    @Override
    public String changePassword(ResetPasswordDto passwordChangeDto) {
        return null;
    }

    @Override
    public String changePassword(NewPasswordDto passwordDto) {
        return null;
    }
}
