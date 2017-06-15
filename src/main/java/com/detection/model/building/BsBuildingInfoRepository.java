package com.detection.model.building;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @fileName BsBuildingInfoRepository.java
 * @author csk
 * @createTime 2017年3月28日 下午12:42:34
 * @version 1.0
 * @function
 */

public interface BsBuildingInfoRepository extends JpaRepository<BsBuildingInfo, Long> {

    /**
     * @createDate 2017年3月31日上午11:36:27
     * @author wangzhiwang
     * @param name
     * @return
     * @description
     */
    /*
     * @Query(value =
     * "SELECT * FROM bs_building_info t where t.street_and_committee like %?1%"
     * , nativeQuery = true) List<BsBuildingInfo>
     * findByStreetAndCommittee(String name);
     */
    /**
     * @createDate 2017年3月31日下午4:53:57
     * @author wangzhiwang
     * @param id
     * @param name
     * @return
     * @description
     */
    @Query(value = "SELECT * FROM bs_building_info t where t.street_id = ?1 and t.property_company_name like %?2% order by t.import_time desc", nativeQuery = true)
    List<BsBuildingInfo> findByStreetIdAndName(Long id, String name);

    List<BsBuildingInfo> findByStreetId(Long id);

    /**
     * @createDate 2017年4月18日上午10:29:21
     * @author wangzhiwang
     * @param name
     * @return
     * @description
     */
    @Query(value = "SELECT * FROM bs_building_info t where  t.property_company_name like %?1% order by t.import_time desc", nativeQuery = true)
    List<BsBuildingInfo> findByPropertyCompanyName(String name);

    /**
     * @createDate 2017年4月20日上午9:27:01
     * @author wangzhiwang
     * @param arr
     * @return
     * @description
     */
    @Query(value = "SELECT * FROM bs_building_info t where  t.id in ?1", nativeQuery = true)
    List<BsBuildingInfo> findByIds(long[] arr);

    @Query(value = "SELECT * FROM bs_building_info t where  t.item_number like %?1%  limit 0,1", nativeQuery = true)
    BsBuildingInfo findByLikeItemNumber(String reportNum);

    @Transactional
    @Modifying()
    @Query(value = "update bs_building_info t set t.building_type_big = ?1,t.building_type_small = ?2,t.score = ?3,t.height_type = ?4,t.risk_level = ?5 where t.item_number = ?6", nativeQuery = true)
    void update(String buildingTypeBig, String buildingTypeSmall, Double score, String heightType, String riskLevel,
            String reportNum);

    @Transactional
    @Modifying()
    @Query(value = "update bs_building_info t set t.block_id = ?1 where t.id = ?2", nativeQuery = true)
    void updateBlockId(Long id, Long id2);


}
