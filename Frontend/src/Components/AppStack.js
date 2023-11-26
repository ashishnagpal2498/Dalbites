import React, { useState, useEffect } from "react";
import { NavigationContainer } from "@react-navigation/native";
import { useDispatch, useSelector } from "react-redux";
import { createStackNavigator } from "@react-navigation/stack";
import * as SecureStore from "expo-secure-store";
import Loading from "../../screens/Loading";
import { setLoading, setToken } from "../../redux/actions/Authentication";
import AuthStack from "./AuthStack";
import UserHome from "./UserHome";
import RestaurantHome from "./RestaurantHome";

const Stack = createStackNavigator();

const AppStack = () => {
  const loading = useSelector((store) => store.authentication.loading);
  const isAuth = useSelector((store) => store.authentication.isAuth);
  const isRestaurant = useSelector(
    (store) => store.authentication.isRestaurant
  );

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
      <Stack.Navigator>
        {loading ? (
          <Stack.Screen name="Loading" component={Loading} />
        ) : isAuth && isRestaurant ? (
          <Stack.Screen
            name="RestaurantHome"
            options={{ title: "Restaurant" }}
            component={RestaurantHome}
          />
        ) : isAuth ? (
          <Stack.Screen name="UserHome" component={UserHome} />
        ) : (
          <Stack.Screen name="AuthStack" component={AuthStack} />
        )}
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default AppStack;
