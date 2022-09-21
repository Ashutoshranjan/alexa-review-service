package com.alexa.review.controllers;

import ch.qos.logback.core.util.StringCollectionUtil;
import com.alexa.review.dto.GenericResponse;
import com.alexa.review.entities.Review;
import com.alexa.review.enums.Ratting;
import com.alexa.review.exceptions.EntityNotFoundException;
import com.alexa.review.services.impl.ReviewServiceImpl;
import com.alexa.review.utils.GenericResponseUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.bytebuddy.utility.nullability.MaybeNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.Cacheable;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/review")
public class ReviewController {

    @Autowired
    private ReviewServiceImpl service;

    @GetMapping
    @Cacheable(value = "reviews")
    public ResponseEntity<GenericResponse> getReviews() throws EntityNotFoundException {
        List<Review> productReviews = service.findAll();
        if (productReviews.isEmpty()) {
            throw new EntityNotFoundException("No reviews found.");
            //return it in message
        }
        return ResponseEntity.ok(GenericResponseUtils.buildGenericResponseOK(productReviews));
    }

     @PutMapping
     @ApiOperation(value = "Create new review or update existing review if already present")
     /*public ResponseEntity<GenericResponse> addOrUpdateReview(@MaybeNull @RequestBody final String review, @NonNull final String author,
                                                              @NonNull @ApiParam("review_source") final String reviewSource,
                                                              @NonNull final Integer rating, final String title, @ApiParam("product_name") final String productName
    )*/
   public ResponseEntity<GenericResponse> addOrUpdateReview(@RequestBody Review reviewDetails)throws EntityNotFoundException {
        //@ApiParam("reviewed_date") String reviewedDate //check for ratting 1to5
        //Uniqueness of a record is derived from author, review source and date.
        //EnumSet<Ratting> validRattings = EnumSet.of(Ratting.ONE, Ratting.TWO, Ratting.THREE, Ratting.FOUR, Ratting.FIVE);
        Review productReview = new Review();
            if(reviewDetails.getRating()<1 || reviewDetails.getRating()>5  || reviewDetails.getAuthor().isEmpty() || reviewDetails.getReview_source().isEmpty()) {
                return ResponseEntity.unprocessableEntity().body(GenericResponseUtils.buildGenericResponseError(reviewDetails));
            }
            else
                productReview = service.createOrUpdate(reviewDetails);
        return ResponseEntity.ok(GenericResponseUtils.buildGenericResponseOK(productReview));
//                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

        /*if (productReview.isEmpty()) {
            throw new EntityNotFoundException("No reviews found.");
        }*/

    }




}
