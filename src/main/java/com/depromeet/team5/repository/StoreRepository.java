package com.depromeet.team5.repository;

import com.depromeet.team5.domain.Store;
import com.depromeet.team5.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends PagingAndSortingRepository<Store, Long> {

    @Query(value = "SELECT *, (" +
            "    6371 * acos (" +
            "      cos ( radians( :latitude ) )  " +
            "      * cos( radians( latitude ) )" +
            "      * cos( radians( longitude ) - radians( :longitude ) )" +
            "      + sin ( radians( :latitude ) )" +
            "      * sin( radians( latitude ) )" +
            "    )" +
            "  ) AS distance" +
            "  FROM store" +
            "  ORDER BY distance", nativeQuery = true)
    Page<Store> findAllByAddress(@Param("latitude") final Double latitude,
                                 @Param("longitude") final Double longitude,
                                 Pageable pageable);

    @Query(value = "SELECT *, (" +
            "    6371 * acos (" +
            "      cos ( radians( :latitude ) )  " +
            "      * cos( radians( latitude ) )" +
            "      * cos( radians( longitude ) - radians( :longitude ) )" +
            "      + sin ( radians( :latitude ) )" +
            "      * sin( radians( latitude ) )" +
            "    )" +
            "  ) AS distance" +
            "  FROM store" +
            "  WHERE category LIKE :category" +
            "  GROUP BY id" +
            "  HAVING distance >= :radiusStart AND distance < :radiusEnd" +
            "  ORDER BY distance", nativeQuery = true)
    List<Store> findAllByDistance(@Param("latitude") final Double latitude,
                                  @Param("longitude") final Double longitude,
                                  @Param("radiusStart") final Double radiusStart,
                                  @Param("radiusEnd") final Double radiusEnd,
                                  @Param("category") final String category);

    @Query(value = "SELECT *, (" +
            "    6371 * acos (" +
            "      cos ( radians( :latitude ) )  " +
            "      * cos( radians( latitude ) )" +
            "      * cos( radians( longitude ) - radians( :longitude ) )" +
            "      + sin ( radians( :latitude ) )" +
            "      * sin( radians( latitude ) )" +
            "    )" +
            "  ) AS distance" +
            "  FROM store" +
            "  WHERE category LIKE :category AND rating >= :ratingStart AND rating < :ratingEnd" +
            "  GROUP BY id" +
            "  HAVING distance <= 1" +
            "  ORDER BY distance", nativeQuery = true)
    List<Store> findAllByReview(@Param("latitude") final Double latitude,
                                @Param("longitude") final Double longitude,
                                @Param("category") final String category,
                                @Param("ratingStart") final Float ratingStart,
                                @Param("ratingEnd") final Float ratingEnd);

    Page<Store> findAllByUser(User user, Pageable pageable);
}
