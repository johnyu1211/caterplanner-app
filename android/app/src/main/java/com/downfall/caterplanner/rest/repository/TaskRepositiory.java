package com.downfall.caterplanner.rest.repository;

import android.database.Cursor;

import com.downfall.caterplanner.rest.model.Task;
import com.downfall.caterplanner.rest.db.SQLiteHelper;

public class TaskRepositiory extends BaseRepository{

    public TaskRepositiory(SQLiteHelper helper) {
        super(helper);
    }

    public void insert(long headerId, Task task){
        final String sql =
                "insert into task values(?, ?, ?)";
        db.execSQL(sql, new String[]{String.valueOf(headerId), String.valueOf(task.getGoalId()), String.valueOf(task.getPerformId())});
    }

    public Task selectByHeaderIdAndPreviousGoalId(long headerId, int previousGoalId){
        final String sql =
                "select header_id, goal_id, perform_id, previous_goal_id from task where header_id = ? and previous_goal_id = ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(headerId), String.valueOf(previousGoalId)});
        Task task = null;
        if(c.moveToNext()){
            task = Task.builder()
                    .headerId(c.getLong(1))
                    .goalId(c.getInt(2))
                    .performId(c.getInt(3))
                    .build();
        }
        return task;
    }

    public Task[] selectActive(){
        final String sql =
                "select header_id, goal_id, perform_id, previous_goal_id from task where previous_goal_id is null";
        Cursor c = db.rawQuery(sql, null);
        Task[] tasks = new Task[c.getCount()];

        while(c.moveToNext()){
            tasks[c.getPosition()] =
                    Task.builder()
                    .headerId(c.getLong(0))
                    .goalId(c.getInt(1))
                    .performId(c.getInt(2))
                    .build();
        }
        return tasks;
    }

    @Deprecated
    public int[] selectGoalIdByPreviousGoalId(long headerId, int previousGoalId){
        final String sql =
                "select goal_id from task where header_id = ? and previous_goal_id = ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(headerId), String.valueOf(previousGoalId)});
        int[] goalIdList = new int[c.getCount()];
        while(c.moveToNext()){
            goalIdList[c.getPosition()] = c.getInt(0);
        }
        return goalIdList;
    }

    public void updateActive(long headerId, int previousGoalId){
        final String sql =
                "update task set previous_goal_id = null where header_id = ? and previous_goal_id = ?";
        db.execSQL(sql, new String[]{String.valueOf(headerId), String.valueOf(previousGoalId)});
    }

    public void deleteByHeaderIdAndGoalId(long headerId, int goalId){
        final String sql =
                "delete from task where header_id = ? and previous_goal_id = ?";
        db.execSQL(sql, new String[]{String.valueOf(headerId), String.valueOf(goalId)});
    }

    public void deleteByHeaderID(long headerId){
        final String sql =
                "delete from task where header_id = ?";
        db.execSQL(sql, new String[]{String.valueOf(headerId)});
    }


}
