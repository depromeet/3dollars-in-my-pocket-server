package com.depromeet.team5.repository;

import com.depromeet.team5.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store,Long> {

    @Modifying
    @Query(value = "SELECT *, (" +
            "    3959 * acos (" +
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
    List<Store> findAllByAddress(@Param("latitude") final float latitude,
                                 @Param("longitude") final float longitude);

    @Modifying
    @Query(value = "SELECT *, (" +
            "    3959 * acos (" +
            "      cos ( radians( :latitude ) )  " +
            "      * cos( radians( latitude ) )" +
            "      * cos( radians( longitude ) - radians( :longitude ) )" +
            "      + sin ( radians( :latitude ) )" +
            "      * sin( radians( latitude ) )" +
            "    )" +
            "  ) AS distance" +
            "  FROM store" +
            "  GROUP BY id" +
            "  HAVING distance >= :radiusStart AND distance <= :radiusEnd" +
            "  ORDER BY distance" +
            "  LIMIT 0 , 20", nativeQuery = true)
    List<Store> findAllByDistance(@Param("latitude") final float latitude,
                                  @Param("longitude") final float longitude,
                                  @Param("radiusStart") final float radiusStart,
                                  @Param("radiusEnd") final float radiusEnd);

    @Modifying
    @Query(value = "SELECT *, (" +
            "    3959 * acos (" +
            "      cos ( radians( :latitude ) )  " +
            "      * cos( radians( latitude ) )" +
            "      * cos( radians( longitude ) - radians( :longitude ) )" +
            "      + sin ( radians( :latitude ) )" +
            "      * sin( radians( latitude ) )" +
            "    )" +
            "  ) AS distance" +
            "  FROM store" +
            "  GROUP BY id" +
            "  HAVING distance >= :radiusStart AND distance <= :radiusEnd" +
            "  ORDER BY review DESC" +
            "  LIMIT 0 , 20", nativeQuery = true)
    List<Store> findAllByReview(@Param("latitude") final float latitude,
                                  @Param("longitude") final float longitude,
                                @Param("radiusStart") final float radiusStart,
                                @Param("radiusEnd") final float radiusEnd);
}
