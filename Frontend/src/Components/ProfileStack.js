import React from "react";
import { createStackNavigator } from "@react-navigation/stack";
import AddReview from "../../screens/AddReview";
import Profile from "../../screens/Profile";

const Stack = createStackNavigator();

const ProfileStack = () => {
  const screenOptions = {
    headerStyle: {
      backgroundColor: "#EAB308",
    },
  };
  return (
    <Stack.Navigator screenOptions={screenOptions}>
      <Stack.Screen
        name="ProfileScreen"
        options={{ title: "Profile" }}
        component={Profile}
      />
      <Stack.Screen
        name="AddReview"
        // options={({ route }) => ({ title: route.params.title })}
        component={AddReview}
      />
    </Stack.Navigator>
  );
};

export default ProfileStack;
