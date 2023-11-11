import React from "react";
import { createStackNavigator } from "@react-navigation/stack";

import AuthScreen from "../../screens/AuthScreen";
import LoginScreen from "../../screens/LoginScreen";
import SignupScreen from "../../screens/SignupScreen";
import VerifyAccount from "../../screens/VerifyAccount";
import ForgetPassword from "../../screens/ForgetPassword";
import Cart from "../../screens/Cart";

const Stack = createStackNavigator();

const AuthStack = () => {
  const screenOptions = {
    headerStyle: {
      backgroundColor: "black",
    },
    headerTintColor: "white",
    headerTitleStyle: {
      fontWeight: "bold",
    },
    headerTitle: "",
  };
  return (
    <Stack.Navigator screenOptions={screenOptions}>
      <Stack.Screen name="Auth" component={AuthScreen} />
      <Stack.Screen name="Login" component={LoginScreen} />
      <Stack.Screen name="Signup" component={SignupScreen} />
      <Stack.Screen name="VerifyAccount" component={VerifyAccount} />
      <Stack.Screen name="ForgetPassword" component={ForgetPassword} />

      <Stack.Screen name="Cart" component={Cart} />
    </Stack.Navigator>
  );
};

export default AuthStack;
