import React, { useEffect, useState } from "react";
import { View, Text, TextInput, Pressable } from "react-native";
import { useDispatch, useSelector } from "react-redux";
import { login, setError } from "../redux/actions/Authentication";
import { emailRegex, usernameRegex } from "../src/Utils/Regex";
import tw from "twrnc";

const LoginScreen = ({ navigation }) => {
  const dispatch = useDispatch();
  const [userId, setUserId] = useState("");
  const [password, setPassword] = useState("");

  const error = useSelector((store) => store.authentication.error);
  const errorCode = useSelector((store) => store.authentication.errorCode);
  const [userIdError, setUserIdError] = useState("");

  const validateCredentials = () => {
    const validUserId = usernameRegex.test(userId) || emailRegex.test(userId);
    !validUserId && setUserIdError("Invalid BannerId/Username");
    return validUserId;
  };

  const handleLogin = async () => {
    if (!validateCredentials()) {
      setTimeout(() => setUserIdError(""), 4000);
      return;
    }
    dispatch(login({ userId, password }));
  };

  useEffect(() => {
    error && setTimeout(() => dispatch(setError({ error: null })), 5000);
  }, [error]);
  return (
    <View style={tw`flex-1 justify-center items-center bg-white`}>
      <Text style={tw`text-black text-4xl font-bold mb-4`}>Login</Text>
      <Text style={{ color: "red" }}>
        {error && `Error - ${error} ErrorCode - ${errorCode}`}
      </Text>
      <Text style={tw`text-red-600 mb-1`}> {userIdError}</Text>
      <TextInput
        style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-4`}
        placeholder="Banner Id / Username"
        value={userId}
        onChangeText={setUserId}
      />
      <TextInput
        style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-4`}
        placeholder="Password"
        secureTextEntry
        value={password}
        onChangeText={setPassword}
      />
      <Text
        onPress={() => navigation.navigate("ForgetPassword")}
        style={tw`underline text-blue-600 mb-3`}
      >
        Forgot Password ?
      </Text>
      <Pressable
        style={tw`bg-yellow-500 rounded-lg py-2 px-4`}
        onPress={handleLogin}
      >
        <Text style={tw`text-black text-lg font-semibold`}>Login</Text>
      </Pressable>
    </View>
  );
};

export default LoginScreen;
