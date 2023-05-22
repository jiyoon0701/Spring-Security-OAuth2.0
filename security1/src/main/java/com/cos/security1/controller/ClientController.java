package com.cos.security1.controller;

import com.cos.security1.config.auth.ClientDetailsImpl;
import com.cos.security1.model.ClientType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;


@Controller
@Slf4j
@RequestMapping("/client")
public class ClientController {

    @Autowired private ClientRegistrationService clientRegistrationService;

    @GetMapping("/register")
    public ModelAndView registerPage(ModelAndView mav) {
        log.info("registerPage 호출");
        mav.setViewName("client/register");
        mav.addObject("registry", new com.cos.secuccrity1.model.RegisterClientInfo());
        return mav;
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard(ModelAndView mv) {
        mv.addObject("applications",
                clientRegistrationService.listClientDetails());
        return mv;
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid com.cos.secuccrity1.model.RegisterClientInfo clientDetails, ModelAndView mav , BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return new ModelAndView("client/register");
        }

        ClientDetailsImpl client = new ClientDetailsImpl();
        client.addAdditionalInformation("name", clientDetails.getName());
        client.setRegisteredRedirectUri(new HashSet<>(Arrays.asList("http://localhost:9000/callback")));
        client.setClientType(ClientType.PUBLIC);
        client.setClientId(UUID.randomUUID().toString());
        client.setClientSecret(UUID.randomUUID().toString());
        client.setAccessTokenValiditySeconds(3600);
        client.setScope(Arrays.asList("read","write"));
        clientRegistrationService.addClientDetails(client);

        mav.setViewName("redirect:/client/dashboard");

        return mav;
    }

    @GetMapping("/remove")
    public ModelAndView remove(
            @RequestParam(value = "client_id", required = false) String clientId) {

        clientRegistrationService.removeClientDetails(clientId);

        ModelAndView mv = new ModelAndView("redirect:/client/dashboard");
        mv.addObject("applications",
                clientRegistrationService.listClientDetails());
        return mv;
    }

}
