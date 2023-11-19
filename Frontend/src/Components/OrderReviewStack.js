import React from "react";
import { createStackNavigator } from "@react-navigation/stack";
import Orders from "../../screens/Orders";
import AddReview from "../../screens/AddReview";

const Stack = createStackNavigator();

const OrderReviewStack = () => {
  const screenOptions = {
    headerStyle: {
      backgroundColor: "#EAB308",
    },
  };
  return (
    <Stack.Navigator screenOptions={screenOptions}>
      <Stack.Screen
        name="OrderHistory"
        options={{ title: "Orders" }}
        component={Orders}
      />
      <Stack.Screen
        name="AddReview"
        options={{ title: "Review" }}
        component={AddReview}
      />
    </Stack.Navigator>
  );
};

export default OrderReviewStack;
