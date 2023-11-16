import React, { useState, useEffect } from "react";
import { NavigationContainer } from "@react-navigation/native";
import { useDispatch, useSelector } from "react-redux";
import { createStackNavigator } from "@react-navigation/stack";
import * as SecureStore from "expo-secure-store";

import Loading from "../../screens/Loading";
import { setLoading, setToken } from "../../redux/actions/Authentication";
import SideDrawer from "./SideDrawer";
import AuthScreen from "../../screens/AuthScreen";
import LoginScreen from "../../screens/LoginScreen";
import SignupScreen from "../../screens/SignupScreen";
import AuthStack from "./AuthStack";
import AddMenu from "../../screens/AddMenu";
import RestaurantSideDrawer from "./RestaurantSideDrawer";

const Stack = createStackNavigator();

const AppStack = () => {
  const loading = useSelector((store) => store.authentication.loading);
  const isAuth = useSelector((store) => store.authentication.isAuth);
  const isRestaurant = useSelector((store) => store.authentication.isRestaurant);

  const dispatch = useDispatch();

  const checkAuth = async () => {
    const userToken = await SecureStore.getItemAsync("token");
    const isRestaurant = await SecureStore.getItemAsync("isRestaurant");
    const restaurantId = await SecureStore.getItemAsync("restaurantId");

    if (userToken) {
      dispatch(setToken({ token: userToken, isRestaurant, restaurantId }));
    } else {
      console.log(" AppStack Component -->Token Not Found");
      dispatch(setLoading({ loading: false }));
    }
  };

  useEffect(() => {
    checkAuth();
  }, []);
  const screenOptions = {
    headerTitle: "",
  };
  return (
    <NavigationContainer>
      <Stack.Navigator screenOptions={{ headerShown: false }}>
        {loading ? (
          <Stack.Screen name="Loading" component={Loading} />
        ) : isAuth && isRestaurant ? (
          <Stack.Screen name="RestaurantSideDrawer" component={RestaurantSideDrawer} />
        ) : 
        isAuth ? (
          <Stack.Screen name="SideDrawer" component={SideDrawer} />
        ) : (
          <Stack.Screen name="AuthStack" component={AuthStack} />
        )}
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default AppStack;
