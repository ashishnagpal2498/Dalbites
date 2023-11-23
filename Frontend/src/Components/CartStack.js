import React from "react";
import { createStackNavigator } from "@react-navigation/stack";
import { TouchableOpacity } from "react-native";
import tw from "twrnc";
import Payment from "../../screens/Payment";
import Cart from "../../screens/Cart";

const Stack = createStackNavigator();

const CartStack = ({ navigation }) => {
  const screenOptions = {
    headerStyle: {
      backgroundColor: "#EAB308",
    },
  };
  return (
    <Stack.Navigator screenOptions={screenOptions}>
      <Stack.Screen
        name="Cart"
        options={{ title: "Cart" }}
        component={Cart}
      />
      <Stack.Screen name="Payment" component={Payment} />
    </Stack.Navigator>
  );
};

export default CartStack;
