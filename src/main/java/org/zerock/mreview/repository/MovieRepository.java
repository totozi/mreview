package org.zerock.mreview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m\n" +
            "    , mi\n" +
            "    , avg(coalesce(r.grade,0))\n" +
            "    , count(distinct r) \n" +
            " FROM Movie m\n" +
            " LEFT OUTER JOIN MovieImage mi on mi.movie = m \n" +
            " LEFT OUTER JOIN Review r on r.movie = m \n" +
            "GROUP BY m")
    Page<Object[]> getListPage(Pageable pageable);


    @Query("SELECT  m  \n" +
            "     , mi \n" +
            "     , avg(coalesce(r.grade,0)) \n" +
            "     , count(distinct(r))      \n" +
            "  FROM Movie m \n" +
            "  LEFT OUTER JOIN MovieImage mi on mi.movie = m \n" +
            "  LEFT OUTER JOIN Review r on r.movie = m \n" +
            " WHERE m.mno = :mno \n" +
            " GROUP BY mi")
    List<Object[]> getMovieWithAll(Long mno); // 특정 영화 조회
}
