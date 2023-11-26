import React from "react";
import {
  createStackNavigator,
  HeaderBackButton,
} from "@react-navigation/stack";
import tw from "twrnc";
import Payment from "../../screens/Payment";
import Cart from "../../screens/Cart";
import OrderDetails from "../../screens/OrderDetails";
import { Text, TouchableOpacity } from "react-native";
import { useNavigation } from "@react-navigation/native";
const Stack = createStackNavigator();

const CartStack = () => {
  const screenOptions = {
    headerStyle: {
      backgroundColor: "#EAB308",
    },
  };
  const navigation = useNavigation();
  return (
    <Stack.Navigator screenOptions={screenOptions}>
      <Stack.Screen name="Cart" options={{ title: "Cart" }} component={Cart} />
      <Stack.Screen name="Payment" component={Payment} />
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

export default CartStack;
