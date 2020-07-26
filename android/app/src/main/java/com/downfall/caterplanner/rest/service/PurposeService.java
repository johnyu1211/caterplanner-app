package com.downfall.caterplanner.rest.service;

import com.downfall.caterplanner.common.model.DetailPlan;
import com.downfall.caterplanner.common.model.Perform;
import com.downfall.caterplanner.common.model.Purpose;
import com.downfall.caterplanner.common.model.StatisticsDetailPlan;
import com.downfall.caterplanner.detailplantree.algorithm.Type;
import com.downfall.caterplanner.rest.db.SQLiteHelper;
import com.downfall.caterplanner.rest.repository.PurposeRepository;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

public class PurposeService extends BaseService {

    private PurposeRepository purposeRepository;
    private DetailPlansService detailPlansService;

    public PurposeService(SQLiteHelper helper, PurposeRepository purposeRepository, DetailPlansService detailPlansService) {
        super(helper);
        this.purposeRepository = purposeRepository;
        this.detailPlansService = detailPlansService;
    }

    public Long create(ReadableMap purpose) throws Exception {
        return purposeRepository.insert(Purpose.valueOf(purpose));
    }

    public Long create(ReadableMap readablePurpose, ReadableArray readableDetailPlans) throws Exception{
        return this.create(readablePurpose, readableDetailPlans, null);
    }

    public Long create(ReadableMap readablePurpose, ReadableArray readableDetailPlans, Long baseId) throws Exception{
        db.beginTransaction();
        try{
            Purpose purpose = Purpose.valueOf(readablePurpose);
            Long detailPlanHeaderId = null;
            if(readableDetailPlans != null){
                detailPlanHeaderId = this.detailPlansService.create(readableDetailPlans, purpose.getAuthorName(), purpose.getAuthorId(), baseId);
            }
            purpose.setDetailPlanHeaderId(detailPlanHeaderId);
            Long id = purposeRepository.insert(purpose);
            db.setTransactionSuccessful();
            return id;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            db.endTransaction();
        }
    }

    public WritableMap readByReact(long id){
        return writableCard(this.purposeRepository.selectById(id));
    }

    public WritableArray readAllByReact(){
        Purpose[] purposes = this.purposeRepository.selectByStatIsActive();
        WritableArray result = Arguments.createArray();
        for(Purpose purpose : purposes){
            result.pushMap(writableCard(purpose));
        }
        return result;
    }

    public void update(long id, Purpose purpose){
        this.purposeRepository.updatePurposeDate(id, purpose);
    }

    public void delete(long id) throws Exception{
        db.beginTransaction();
        try{
            Purpose purpose = this.purposeRepository.selectById(id);
            if(purpose == null)
                throw new Exception();

            this.purposeRepository.deleteById(id);
            this.detailPlansService.delete(purpose.getDetailPlanHeaderId());

            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            db.endTransaction();
        }
    }

    private WritableMap writableCard(Purpose purpose){
        WritableMap writablePurpose = Purpose.parseWritableMap(purpose);

        StatisticsDetailPlan[] detailPlans = (StatisticsDetailPlan[]) this.detailPlansService.read(purpose.getDetailPlanHeaderId());

        purpose.setDetailPlans(detailPlans);

        writablePurpose.putDouble("progress", purpose.progress());
        writablePurpose.putDouble("achieve", purpose.achieve());
        writablePurpose.putInt("leftDay", purpose.getLeftDay());

        WritableArray writableActivePerforms = Arguments.createArray();
        for(StatisticsDetailPlan detailPlan : detailPlans){
            if(detailPlan.getType() == Type.P){
                WritableMap writablePerformInfo = Arguments.createMap();
                Perform perform = (Perform) detailPlan;
                writablePerformInfo.putString("name", perform.getName());
                writablePerformInfo.putDouble("progress", perform.progress());
                writablePerformInfo.putDouble("achieve", perform.achieve());
                writablePerformInfo.putInt("nextDay", perform.getNextLeftDayCount());
                writableActivePerforms.pushMap(writablePerformInfo);
            }
        }

        writablePurpose.putArray("activePerforms", writableActivePerforms);
        return writablePurpose;
    }

}
