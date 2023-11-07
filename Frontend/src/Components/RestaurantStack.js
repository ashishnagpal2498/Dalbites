import React from "react";
import { createStackNavigator } from "@react-navigation/stack";

import Restaurants from "../../screens/Restaurants";
import RestaurantDetails from "../../screens/RestaurantDetails";
import FilterScreen from "../../screens/FilterScreen";
import SearchScreen from "../../screens/SearchScreen";
import { useSelector } from "react-redux";

const Stack = createStackNavigator();

const RestaurantStack = () => {
  const screenOptions = {
    headerShown: null,
  };
  return (
    <Stack.Navigator screenOptions={screenOptions}>
      <Stack.Screen name="ViewRestaurants" component={Restaurants} />
      <Stack.Screen name="RestaurantDetail" component={RestaurantDetails} />
      <Stack.Screen name="FilterRestaurant" component={FilterScreen} />
      <Stack.Screen name="SearchRestaurant" component={SearchScreen} />
    </Stack.Navigator>
  );
};

export default RestaurantStack;
