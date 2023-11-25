import React from "react";
import { createStackNavigator } from "@react-navigation/stack";
import Orders from "../../screens/Orders";
import AddReview from "../../screens/AddReview";
import OrderDetails from "../../screens/OrderDetails";

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
      <Stack.Screen
        name="OrderDetails"
        options={{
          title: "Order Details",
        }}
        component={OrderDetails}
      />
    </Stack.Navigator>
  );
};

export default OrderReviewStack;
