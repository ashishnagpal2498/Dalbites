import React, { useEffect } from "react";
import { View, Text, TouchableOpacity, Image, StyleSheet } from "react-native";
import tw from "twrnc";
import { useSelector } from "react-redux";
const WelcomeScreen = ({ navigation }) => {
  const redirect = useSelector((store) => store.authentication.redirect);

  useEffect(() => {
    console.log("REDIRECT ----> ", redirect);
    if (redirect) {
      navigation.navigate(redirect);
    }
  }, []);
  return (
    <View style={tw`flex-1 justify-center items-center bg-white`}>
      <Image
        source={require("../assets/images/DalBites.png")}
        style={[styles.image, { marginTop: -60 }]}
      />
      <Text style={tw`text-black text-3xl font-bold mb-8`}>
        Welcome to DalBites
      </Text>
      <TouchableOpacity
        style={tw`bg-yellow-500 rounded-lg py-2 px-4`}
        onPress={() => navigation.navigate("Login")}
      >
        <Text style={tw`text-black text-lg font-semibold`}>Login</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={tw`mt-4 bg-yellow-500 rounded-lg py-2 px-4`}
        onPress={() => navigation.navigate("Signup")}
      >
        <Text style={tw`text-black text-lg font-semibold`}>Sign Up</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  image: {
    width: 300,
    height: 300,
    resizeMode: "contain",
  },
});

export default WelcomeScreen;
