import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  View,
  KeyboardAvoidingView,
  Keyboard,
  Text,
  TextInput,
  Pressable,
  TouchableWithoutFeedback,
  ScrollView,
} from "react-native";
import tw from "twrnc";
import { emailRegex, usernameRegex, passwordRegex } from "../src/Utils/Regex";
import { setError, signUp } from "../redux/actions/Authentication";

const SignupScreen = () => {
  const dispatch = useDispatch();
  const error = useSelector((store) => store.authentication.error);
  const [disabled, setDisabled] = useState(true);
  const [signUpFormData, setSignUpFormData] = useState({
    username: "",
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
    role: "user", // Keeping it constant - Change while Demo
  });

  const [errors, setValidationErrors] = useState({
    username: "",
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const handleSignUpState = (field, value) => {
    setSignUpFormData({
      ...signUpFormData,
      [field]: value,
    });
  };

  const validateCredentials = () => {
    const { username, name, email, password, confirmPassword } = signUpFormData;
    const isError = { ...errors };

    if (!usernameRegex.test(username)) isError.username = "Invalid B00 Id";

    if (!emailRegex.test(email)) isError.email = "Please enter a valid email";

    if (name.trim() === "") isError.name = "Please enter a valid name";

    if (!passwordRegex.test(password))
      isError.password =
        "Password must have 8 characters, one lowecase uppercase letter number and special character:";

    if (password !== confirmPassword)
      isError.confirmPassword = "Password Mismatch";

    setValidationErrors(isError);
    console.log(isError, "-----> ", errors);
    return Object.values(isError).some(
      (value) => typeof value === "string" && value.trim() !== ""
    );
  };

  const handleSignup = async () => {
    if (validateCredentials()) {
      setTimeout(() => setValidationErrors({}), 4000);
      return;
    }
    // Dispatch the Reducer Action
    let payload = { ...signUpFormData };
    delete payload["confirmPassword"];
    dispatch(signUp(payload));
  };

  useEffect(() => {
    Object.values(signUpFormData).some(
      (value) => typeof value === "string" && value.trim() !== ""
    )
      ? setDisabled(false)
      : setDisabled(true);
  }, [signUpFormData]);

  useEffect(() => {
    error &&
      setTimeout(
        () => dispatch(setError({ error: null, errorType: null })),
        5000
      );
  }, [error]);
  return (
    <KeyboardAvoidingView
      behavior="height"
      style={tw`flex-1 justify-center items-center bg-white`}
    >
      <TouchableWithoutFeedback onPress={Keyboard.dismiss}>
        <ScrollView
          contentContainerStyle={[
            tw`flex-1 justify-center items-center`,
            { minHeight: 500 },
          ]}
        >
          <Text style={tw`text-black text-4xl font-bold mb-8`}>Sign Up</Text>
          <Text style={tw`text-red-600 mb-1`}> {error}</Text>
          <Text style={tw`text-red-600 mb-1`}> {errors.name}</Text>
          <TextInput
            style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-2`}
            placeholder="Name"
            value={signUpFormData.name}
            onChangeText={(text) => handleSignUpState("name", text)}
          />

          <Text style={tw`text-red-600 mb-1`}>{errors.username}</Text>
          <TextInput
            style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-2`}
            placeholder="Banner Id"
            value={signUpFormData.username}
            onChangeText={(text) => handleSignUpState("username", text)}
          />

          <Text style={tw`text-red-600 mb-1`}>{errors.email}</Text>
          <TextInput
            style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-2`}
            placeholder="Email"
            value={signUpFormData.email}
            onChangeText={(text) => handleSignUpState("email", text)}
          />

          <Text style={tw`text-red-600 mb-1`}>{errors.password}</Text>
          <TextInput
            style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-1`}
            placeholder="Password"
            secureTextEntry
            value={signUpFormData.password}
            onChangeText={(text) => handleSignUpState("password", text)}
          />
          <Text style={tw`text-red-600 mb-1`}>{errors.confirmPassword}</Text>
          <TextInput
            style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-2`}
            placeholder="Confirm Password"
            secureTextEntry
            value={signUpFormData.confirmPassword}
            onChangeText={(text) => handleSignUpState("confirmPassword", text)}
          />
        </ScrollView>
      </TouchableWithoutFeedback>
      <Pressable
        style={[
          tw`bg-yellow-500 rounded-lg py-2 px-4 my-3`,
          disabled ? tw`opacity-50` : tw`opacity-100`,
        ]}
        onPress={handleSignup}
        onPressOut={Keyboard.dismiss}
        disabled={disabled}
      >
        <Text style={tw`text-black text-lg font-semibold`}>Sign Up</Text>
      </Pressable>
    </KeyboardAvoidingView>
  );
};

export default SignupScreen;
