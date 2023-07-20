package com.vabiss.okrbackend.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/invitation")
public class InvitationController {

    @PostMapping("/accept/{workspaceId}")
    public void acceptInvitation(@PathVariable int workspaceId) {


        System.out.println("accepted the invitation");
    }
}
