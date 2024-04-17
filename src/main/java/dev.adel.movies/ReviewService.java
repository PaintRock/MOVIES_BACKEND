package dev.adel.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReviews(String reviewBody, String imdbId) {
        // Create a new review
        Review review = reviewRepository.insert(new Review(reviewBody));

        // Update the associated Movie document
        Query query = new Query(Criteria.where("imdbId").is(imdbId));
        Update update = new Update().push("reviewIds", review.getId());
        mongoTemplate.updateFirst(query, update, Movie.class);

        return review;
    }
}
