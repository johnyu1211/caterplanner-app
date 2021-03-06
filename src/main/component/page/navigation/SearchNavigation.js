import React, { Component } from 'react';
import recommend from '../screen/search/RecommendGoals'
import newGoals from '../screen/search/NewGoals'
import popularGaols from '../screen/search/PopularGaols'
import category from '../screen/search/Category'
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';

const Tab = createMaterialTopTabNavigator();

const Search = () => {
  return(
    <Tab.Navigator  
      tabBarOptions={{
      activeTintColor: 'black',
      inactiveTintColor: 'gray',
      tabBarPosition: "right",
    }}>
    <Tab.Screen name="추천 목표" component={recommend} />
    <Tab.Screen name="최신 목표" component={newGoals} />
    <Tab.Screen name="인기 목표" component={popularGaols} />
    <Tab.Screen name="카테고리" component={category} />
  </Tab.Navigator>
  );
}

export default Search;
