package com.downfall.caterplanner.common.model;

import com.downfall.caterplanner.detailplantree.algorithm.Type;
import com.downfall.caterplanner.util.DateUtil;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Perform extends StatisticsDetailPlan {

    private Briefing[] briefings;

    private char cycleType;
    private int[] cycleParams;

    public Perform(long key, long headerId,  int constructorKey, int constructorRelationType, String name, Type type, LocalDate startDate, LocalDate endDate, Integer hopeAchievement, String color, String cycle, int stat) {
        super(key, headerId, constructorKey, constructorRelationType, name, type, startDate, endDate, null, color, cycle, stat);
    }

    public Perform(long key, long headerId,  int constructorKey, int constructorRelationType, String name, Type type, LocalDate startDate, LocalDate endDate, Integer hopeAchievement, String color, String cycle, int stat, Briefing[] briefings) {
        super(key, headerId, constructorKey, constructorRelationType, name, type, startDate, endDate, null, color, cycle, stat);

        //cycle 분석
        char[] cyclePiece = cycle.toCharArray();
        List<Integer> t_cycleParams = new ArrayList<>();
        for(int i = 0; i < cyclePiece.length; i++){
            if(i == 0){
                cycleType = cyclePiece[0];
                continue;
            }else if(cyclePiece[i] != 32){
                t_cycleParams.add(Character.getNumericValue(cyclePiece[i]));
            }
        }

        this.maxTime  = Perform.getBetweenMaxBriefing(startDate, endDate, cycleType, cycleParams);
        this.currentPerfectTime = getBetweenMaxBriefing(startDate, LocalDate.now(), cycleType, cycleParams);
        this.briefings = briefings;

        statistion();
    }

    public void setBriefings(Briefing[] briefings) {
        this.briefings = briefings;
        statistion();
    }

    @Override
    public void statistion() {
        if(this.briefings == null)
            throw new RuntimeException();

        this.currentBriefingCount = this.briefings.length;
        this.isStatizable = true;
    }
    public int getNextLeftDayCount(){
        return Period.between(LocalDate.now(), getNextLeftDay()).getDays();
    }
    public LocalDate getNextLeftDay(){

        final LocalDate today = LocalDate.now();
        LocalDate nextDay = null;

        if(isNowBriefing()){
            nextDay = today;
        }else{
            switch (this.cycleType){
                case 'A':
                    nextDay = today.plusDays(1);
                    break;
                case 'W':
                    nextDay = today.plusDays(DateUtil.waitingDayOfWeekCountDay(today, cycleParams[0]));
                    for(int i = 1; i < cycleParams.length; i++){
                        if(cycleParams[i] > today.getDayOfWeek().getValue()){
                            nextDay = today.plusDays(DateUtil.waitingDayOfWeekCountDay(today, cycleParams[i]));
                            break;
                        }
                    }
                    break;
                case 'M':
                    nextDay = today.plusDays(DateUtil.waitingDayCountDay(today, cycleParams[0]));
                    for(int i = 1; i < cycleParams.length; i++){
                        if(cycleParams[i] > today.getDayOfMonth()){
                            nextDay = today.plusDays(DateUtil.waitingDayCountDay(today, cycleParams[i]));
                            break;
                        }
                    }
                    break;
            }
        }
        return nextDay;
    }

    public boolean isNowBriefing(){

        final LocalDate today = LocalDate.now();

        switch (this.cycleType){
            case 'A':
                return !this.getLastBriefingDay().equals(today) ? true : false;
            case 'W':
                for(int wParam : this.cycleParams) {
                    if (wParam == today.getDayOfWeek().getValue()) {
                        return true;
                    }
                }
                return false;
            case 'M':
                for(int mParam : this.cycleParams){
                    if(mParam == today.getDayOfMonth()){
                        return true;
                    }
                }
                return false;
        }
        return false;
    }

    private static int getDayBriefingCountInTerm(LocalDate startDate, LocalDate endDate, int[] piece){
        int count = 0;

        for(int i =0; i< piece.length; i++){
            if(startDate.isBefore(endDate)){
                if(piece[i] >= startDate.getDayOfWeek().getValue() && piece[i] <= endDate.getDayOfWeek().getValue())
                    count++;
            }else{
                if(piece[i] >= startDate.getDayOfMonth() || piece[i] <= endDate.getDayOfMonth())
                    count++;
            }
        }

        return count;
    }

    private static int getBetweenMaxBriefing(LocalDate startDate, LocalDate endDate, char cycleType, int[] pieces){

        final LocalDate today = LocalDate.now();
        LocalDate nextDay;

        final Period diff = Period.between(startDate, endDate);
        int maxTime = 0;

        switch (cycleType){
            case 'A':
                maxTime = diff.getDays();
                break;
            case 'W':
                maxTime = diff.getDays() < 7 ? getDayBriefingCountInTerm(startDate, endDate, pieces) :
                        ((int) Math.floor(diff.getDays() / 7) * pieces.length) + getDayBriefingCountInTerm(endDate.minusDays(diff.getDays() % 7), endDate, pieces);
                break;
            case 'M':
                final int diffMonth = diff.getMonths();
                if(diffMonth == 0){
                    for(int i = 0; i < pieces.length; i++){
                        if(pieces[i] >= startDate.getDayOfMonth() && pieces[i] <= endDate.getDayOfMonth())
                            maxTime++;
                    }
                }else{
                    if(diffMonth > 2)
                        maxTime = pieces.length * (diffMonth - 2);
                    for(int i = 0; i < pieces.length; i++){
                        if(pieces[i] >= startDate.getDayOfMonth()){
                            maxTime += (pieces.length - 1) - i;
                            break;
                        }
                    }
                    for(int i = pieces.length - 1; i >=0 ; i--){
                        if(pieces[i] <= endDate.getDayOfMonth()){
                            maxTime += i + 1;
                            break;
                        }
                    }
                }
                break;
        }
        return  maxTime;
    }

    public LocalDate getLastBriefingDay(){
        return this.briefings[this.briefings.length- 1].getCreateAt();
    }

}
