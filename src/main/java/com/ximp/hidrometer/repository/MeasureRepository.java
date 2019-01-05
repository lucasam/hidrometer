package com.ximp.hidrometer.repository;

import com.ximp.hidrometer.model.Measure;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasureRepository extends JpaRepository<Measure, Integer> {

    public List<Measure> findAllByIdGreaterThan(Integer id);
}
