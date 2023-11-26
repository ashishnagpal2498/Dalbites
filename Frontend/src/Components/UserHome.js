import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import IconTextBar from "../Layouts/IconTextBar";
import RestaurantStack from "./RestaurantStack";
import Colors from "../Utils/Colors";
import OrderReviewStack from "./OrderReviewStack";
import ProfileStack from "./ProfileStack";
import Cart from "../../screens/Cart";
import CartStack from "./CartStack";
const Tab = createBottomTabNavigator();

const UserHome = () => (
  <Tab.Navigator
    screenOptions={({ route }) => ({
      headerShown: false,
      tabBarIcon: ({ focused, color }) => {
        let iconName;
        color = focused ? Colors.black : Colors.primaryButton;
        if (route.name === "Orders") {
          iconType = "Mci";
          iconName = focused ? "file-edit" : "file-edit-outline";
        } else if (route.name === "RestaurantsStack") {
          iconType = "Ini";
          iconName = focused ? "restaurant" : "restaurant-outline";
        } else if (route.name === "CartStack") {
          iconType = "Ini";
          iconName = focused ? "cart" : "cart-outline";
        } else if (route.name === "Profile") {
          iconType = "Fai";
          iconName = focused ? "user-alt" : "user";
        }

        return (
          <IconTextBar
            iconName={iconName}
            iconType={iconType}
            iconOnly={true}
            iconColor={color}
          />
        );
      },
      tabBarActiveTintColor: Colors.primaryButton,
      tabBarInactiveTintColor: Colors.black,
    })}
  >
    <Tab.Screen
      name="RestaurantsStack"
      options={{ title: "Restaurants" }}
      component={RestaurantStack}
    />
    <Tab.Screen name="Orders" component={OrderReviewStack} />
    <Tab.Screen
      name="CartStack"
      options={{ title: "Cart" }}
      component={CartStack}
    />
    <Tab.Screen name="Profile" component={ProfileStack} />
  </Tab.Navigator>
);

export default UserHome;
