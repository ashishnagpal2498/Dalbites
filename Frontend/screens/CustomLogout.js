import React from "react";
import { View, Text, Pressable, StyleSheet } from "react-native";
import { useDispatch } from "react-redux";
import { logout } from "../redux/actions/Authentication";
import tw from "twrnc";

const CustomLogout = () => {
  const dispatch = useDispatch();

  return (
    <View style={styles.container}>
      <Pressable
        style={tw`bg-yellow-500 rounded-lg py-2 px-4 my-3`}
        onPress={() => dispatch(logout())}
      >
        <Text style={tw`text-black text-lg font-semibold`}>Logout</Text>
      </Pressable>
    </View>
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
