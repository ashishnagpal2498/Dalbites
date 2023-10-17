import React from "react";
import { View, Text, Pressable, StyleSheet } from "react-native";
import { useDispatch } from "react-redux";
import { logout } from "../redux/actions/Authentication";
import { DrawerContentScrollView } from "@react-navigation/drawer";

const CustomLogout = () => {
  const dispatch = useDispatch();

  return (
    <DrawerContentScrollView>
      <View style={styles.container}>
        <Text>Logout Screen</Text>
        <Pressable title="Logout" onPress={dispatch(logout())} />
      </View>
    </DrawerContentScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
});
export default CustomLogout;
