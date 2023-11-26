import React, { useState, useRef, useEffect } from "react";
import {
  View,
  Text,
  TextInput,
  StyleSheet,
  Pressable,
  Keyboard,
  ScrollView,
} from "react-native";
import { useSelector, useDispatch } from "react-redux";

import tw from "twrnc";
import {
  setError,
  setLoading,
  setSuccessMessage,
  validateOTP,
} from "../redux/actions/Authentication";

import { passwordRegex } from "../src/Utils/Regex";

const VerifyAccount = () => {
  const tempUser = useSelector((store) => store.authentication.tempUser);
  const error = useSelector((store) => store.authentication.error);
  const successMessage = useSelector(
    (store) => store.authentication.successMessage
  );
  const tempToken = useSelector((store) => store.authentication.tempToken);
  const dispatch = useDispatch();
  const [otp, setOTP] = useState(["", "", "", "", "", ""]);

  const inputRefs = [
    useRef(null),
    useRef(null),
    useRef(null),
    useRef(null),
    useRef(null),
    useRef(null),
  ];

  const [disabled, setDisabled] = useState(true);
  const [newPassword, setNewPassword] = useState({
    password: "",
    confirmPassword: "",
  });

  const [errors, setValidationErrors] = useState({
    password: "",
    confirmPassword: "",
  });

  useEffect(() => {
    console.log("TempUser after SuccessMessage   -> ", tempUser);
    successMessage &&
      setTimeout(() => {
        if (tempUser.role == "user") {
          dispatch(
            setSuccessMessage({
              successMessage: null,
              ...(tempUser.forgetPassword
                ? { redirect: "Login", loading: true }
                : { isAuth: true, redirect: "" }),
            })
          );
          dispatch(setLoading({ loading: false }));
        }
        else {
          dispatch(
            setSuccessMessage({
              successMessage: null,
              ...{ redirect: "SetupRestaurantScreen", loading: true },
            })
          );
          dispatch(setLoading({ loading: false }));
        }
      }, 3000);
  }, [successMessage]);

  const validateCredentials = () => {
    const { password, confirmPassword } = newPassword;
    const isError = { ...errors };

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
  const handleVerifyOTP = () => {
    if (tempUser.forgetPassword && validateCredentials()) {
      setNewPassword({
        password: "",
        confirmPassword: "",
      });
      setOTP(["", "", "", "", "", ""]);

      setTimeout(() => setValidationErrors({}), 4000);
      return;
    }
    const enteredOTP = otp.join("");
    const payload = {
      otp: enteredOTP,
      tempToken,
      ...(tempUser.forgetPassword && { password: newPassword.password }),
    };
    dispatch(validateOTP(payload));
  };
  const maskEmail = (email = "abc@dal.com") => {
    const atIndex = email.indexOf("@");
    if (atIndex > 0) {
      const prefix = email.slice(0, atIndex - 3);
      const maskedPart = "X".repeat(email.length - prefix.length - 3);
      const domainPart = email.slice(atIndex);
      return email.slice(0, 3) + maskedPart + domainPart;
    }
    return email;
  };

  const handleOTPInputChange = (text, index) => {
    const newOTP = [...otp];
    if (/^[0-9]$/.test(text) && index < 6) {
      newOTP[index] = text;
      setOTP(newOTP);

      if (index < 5 && text !== "") {
        setDisabled(true);
        inputRefs[index + 1].current.focus();
      } else if (index === 5) {
        setDisabled(false);
      }
    } else if (text === "" && index > 0) {
      newOTP[index] = "";
      setOTP(newOTP);
    }
  };

  const handleNewPassState = (field, value) => {
    setNewPassword({
      ...newPassword,
      [field]: value,
    });
  };

  useEffect(() => {
    error && setTimeout(() => dispatch(setError({ error: null })), 5000);
  }, [error]);

  return (
    <ScrollView
      contentContainerStyle={[
        tw`flex-1 justify-center items-center`,
        { minHeight: 500 },
      ]}
    >
      <View style={styles.container}>
        <Text style={styles.title}>
          {tempUser.forgetPassword ? "Reset Password" : "Verify OTP"}
        </Text>
        <View style={styles.emailContainer}>
          <Text> An OTP has been sent to your email - </Text>
          <Text>{maskEmail(tempUser.email)}</Text>
          <Text style={error ? tw`text-red-600` : tw`text-green-600`}>
            {error ? error : successMessage}
          </Text>
          <Text style={tw`text-green-600`}>
            {successMessage && "You will be redirected in 3 seconds"}
          </Text>
        </View>
        {tempUser.forgetPassword && (
          <View>
            <Text style={tw`text-red-600 mb-1`}>{errors.password}</Text>
            <TextInput
              style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-1`}
              placeholder="New Password"
              secureTextEntry
              value={newPassword.password}
              onChangeText={(text) => handleNewPassState("password", text)}
            />
            <Text style={tw`text-red-600 mb-1`}>{errors.confirmPassword}</Text>
            <TextInput
              style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-2`}
              placeholder="Confirm Password"
              secureTextEntry
              value={newPassword.confirmPassword}
              onChangeText={(text) =>
                handleNewPassState("confirmPassword", text)
              }
            />
          </View>
        )}
        <View style={styles.otpContainer}>
          {otp.map((digit, index) => (
            <TextInput
              key={index}
              ref={inputRefs[index]}
              style={styles.otpInput}
              keyboardType="numeric"
              onChangeText={(text) => handleOTPInputChange(text, index)}
              value={digit}
              selectionColor={tw`bg-yellow-500`}
            />
          ))}
        </View>
        <Pressable
          style={[
            tw`bg-yellow-500 rounded-lg py-2 px-4 my-3`,
            disabled ? tw`opacity-50` : tw`opacity-100`,
          ]}
          onPress={handleVerifyOTP}
          onPressOut={Keyboard.dismiss}
          disabled={disabled}
        >
          <Text style={tw`text-black text-lg font-semibold`}>Verify</Text>
        </Pressable>
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  emailContainer: {
    fontSize: 16,
    marginBottom: 2,
    alignItems: "center",
  },
  title: {
    fontSize: 20,
    marginBottom: 20,
  },
  otpContainer: {
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    marginBottom: 10,
    marginTop: 10,
  },
  otpInput: {
    borderWidth: 1,
    borderColor: "gray",
    padding: 10,
    width: 35,
    textAlign: "center",
    margin: 5,
    fontSize: 20,
    borderRadius: 10,
  },
});

export default VerifyAccount;
