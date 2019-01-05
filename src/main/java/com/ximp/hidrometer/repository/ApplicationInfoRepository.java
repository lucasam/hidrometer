package com.ximp.hidrometer.repository;

import com.ximp.hidrometer.model.ApplicationInfo;
import com.ximp.hidrometer.model.Measure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationInfoRepository extends JpaRepository<ApplicationInfo, String> {

}
