import React, { useEffect, useState } from "react";
import { View, Text, TextInput, Pressable } from "react-native";
import { useDispatch, useSelector } from "react-redux";
import { forgetPassword, setError } from "../redux/actions/Authentication";
import { usernameRegex } from "../src/Utils/Regex";
import tw from "twrnc";

const ForgetPassword = () => {
  const dispatch = useDispatch();
  const [username, setUsername] = useState("");

  const error = useSelector((store) => store.authentication.error);

  const [usernameError, setUsernameError] = useState("");

  const validateCredentials = () => {
    const invalidUsername = !usernameRegex.test(username);
    invalidUsername && setUsernameError("Invalid B00 Id");
    return invalidUsername;
  };

  const handleForgetPassword = async () => {
    if (validateCredentials()) {
      setTimeout(() => setUsernameError(""), 4000);
      return;
    }
    dispatch(forgetPassword({ username }));
  };

  useEffect(() => {
    error &&
      setTimeout(
        () => dispatch(setError({ error: null, errorType: null })),
        5000
      );
  }, [error]);
  return (
    <View style={tw`flex-1 justify-center items-center bg-white`}>
      <Text style={tw`text-black text-4xl font-bold mb-4`}>
        Forget Password
      </Text>
      <Text style={{ color: "red" }}>{error && error}</Text>
      <Text style={tw`text-red-600 mb-1`}> {usernameError}</Text>
      <TextInput
        style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-4`}
        placeholder="Banner Id"
        value={username}
        onChangeText={setUsername}
      />
      <Pressable
        style={tw`bg-yellow-500 rounded-lg py-2 px-4`}
        onPress={handleForgetPassword}
      >
        <Text style={tw`text-black text-lg font-semibold`}>Reset Password</Text>
      </Pressable>
    </View>
  );
};

export default ForgetPassword;
