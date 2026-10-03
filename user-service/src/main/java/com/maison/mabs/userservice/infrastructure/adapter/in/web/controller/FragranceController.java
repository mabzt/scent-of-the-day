package com.maison.mabs.userservice.infrastructure.adapter.in.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Fragrance Controller", description = "Fragrance recommendation  endpoints")
public class FragranceController {

}
