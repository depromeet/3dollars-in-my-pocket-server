package com.depromeet.team5.repository;

import com.depromeet.team5.domain.store.Store;
import com.depromeet.team5.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
            "  ORDER BY distance" +
            "  LIMIT 0 , 5", nativeQuery = true)
    List<Store> findAllByAddress(@Param("latitude") final Double latitude,
                                 @Param("longitude") final Double longitude);

    @Query(value = "SELECT *, (" +
            "    6371 * acos(" +
            "      cos( radians( :latitude ) )  " +
            "      * cos( radians( latitude ) )" +
            "      * cos( radians( longitude ) - radians( :longitude ) )" +
            "      + sin( radians( :latitude ) )" +
            "      * sin( radians( latitude ) )" +
            "    )" +
            "  ) AS distance" +
            "  FROM store s" +
            "  WHERE s.category = :category" +
            "  GROUP BY s.id" +
            "  HAVING distance >= :radiusStart AND distance < :radiusEnd" +
            "  ORDER BY distance", nativeQuery = true)
    List<Store> findByDistanceBetweenAndCategory(@Param("latitude") final Double latitude,
                                                 @Param("longitude") final Double longitude,
                                                 @Param("radiusStart") final Double distanceStart,
                                                 @Param("radiusEnd") final Double distanceEnd,
                                                 @Param("category") final String category);

    Page<Store> findAllByUser(User user, Pageable pageable);

    Slice<Store> findByIdGreaterThan(Long storeId, Pageable pageable);
}
