import React, { useState, useEffect } from "react";
import { View, Text, TouchableOpacity, StyleSheet,Image} from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import * as ImagePicker from "expo-image-picker";


const userData = {
  id: 1,
  name: 'Adam Bills ',
  banner: 'B00123456',
  email: 'abcd@dal.ca',
};

const orderHistory = [
  {
    orderId: '00001',
    date: '09-11-2023',
    restaurantName: 'Starbucks',
    items: ['Expresso', 'Latte', 'Cappuccino','Donut'],
    quantities: [1, 2, 1, 4],
    totalCost: 25.99,
  },
  {
    orderId: '00002',
    date: '08-11-2023',
    restaurantName: 'Pizza Pizza',
    items: ['Pizza', 'Salad'],
    quantities: [1, 1],
    totalCost: 19.99,
  },
  {
    orderId: '00003',
    date: '07-11-2023',
    restaurantName: 'Tim Hortons',
    items: ['Timbits', 'French Vanilla'],
    quantities: [1, 3],
    totalCost: 20.99,
  },
  {
    orderId: '00004',
    date: '06-11-2023',
    restaurantName: 'KFC',
    items: ['Burger', 'Chicken Bucket'],
    quantities: [3, 1],
    totalCost: 35.99,
  },
];

const Profile = ({navigation}) => {

  // const handleEditPhoto = () => {
  //   console.log('edit photo button pressed');
  // };

  const handleChangePassword = () => {
    console.log('change password button pressed');
  };

  const handleLogout = () => {
    console.log('logout button pressed');
  };

  const handlereview = (orderId) => {
    navigation.navigate('Review', { orderId });
  };

  const [userPhoto, setUserPhoto] = useState(
    require("../assets/images/Dummy_profile_photo.png")
  );

  useEffect(() => {
    // Request permission to access the user's media library
    (async () => {
      const { status } = await ImagePicker.requestMediaLibraryPermissionsAsync();
      if (status !== "granted") {
        alert("Sorry, we need camera roll permissions to make this work!");
      }
    })();
  }, []);

  const handleEditPhoto = async () => {
    // Launch the image picker
    const result = await ImagePicker.launchImageLibraryAsync({
      mediaTypes: ImagePicker.MediaTypeOptions.Images,
      allowsEditing: true,
      aspect: [1, 1],
      quality: 1,
    });

    if (!result.canceled) {
      // Update the user's photo with the selected image
      setUserPhoto({ uri: result.uri });
    }
  };

  return (
    <ScrollView style={styles.container}>

      <View style={styles.header}>
        <View style={styles.extramheader}>
          <Text style={styles.headertext}>Profile</Text>
        </View>
      </View>

      <View style={styles.profilebody}>

        <View style={styles.profileinfocontainer}>

          <View style={styles.profilephotoContainer}>

            {/* <View style={styles.photoContainer}>
              <Image
                style={styles.photo}
                source={require("../assets/images/Dummy_profile_photo.png")}
              />
            </View> */}

            <View style={styles.photoContainer}>
              <Image style={styles.photo} source={userPhoto} />
            </View>

            <TouchableOpacity style={styles.photobutton} onPress={handleEditPhoto}>
              <Text style={styles.phototext}>Edit Photo</Text>
            </TouchableOpacity>
            
          
          </View>
  
          <View style={styles.profileinfo}>
            <Text style={styles.nametext}>Name : {userData.name}</Text>
            <Text style={styles.emailtext}>Email : {userData.email}</Text>
            <Text style={styles.bannertext}>Banner Id : {userData.banner}</Text>
          </View>

        </View>

        <View style={styles.profilebuttonscontainer}>

          <TouchableOpacity style={styles.button} onPress={handleChangePassword}>
            <Text style={styles.buttonText}>Change Password</Text>
          </TouchableOpacity>

          <TouchableOpacity style={styles.button} onPress={handleLogout}>
            <Text style={styles.buttonText}>Logout</Text>
          </TouchableOpacity>

        </View>

      </View>

      <View style={styles.header}>
        <View style={styles.extrahleader}>
          <Text style={styles.headertext}>Order History</Text>
        </View>
      </View>

      <View style={styles.ordercontainer}>
        {orderHistory.map((order, index) => (
          <View style={styles.ordercard} key={index}>
            <View style={styles.row1}>
              <Text style={styles.row1text}>Order ID: {order.orderId}</Text>
              <Text style={styles.row1text}>Date: {order.date}</Text>
            </View>

            <View style={styles.row2}>
              <Text style={styles.row2text}>{order.restaurantName}</Text>
              <Text style={styles.row2text}>Cost: ${order.totalCost}</Text>
            </View>

            <View style={styles.row3}>
              <Text style={styles.row3text}>Order: </Text>
              <Text style={styles.row3text}>
                {order.items.map((item, i) => (
                  `${item}(${order.quantities[i]})${i === order.items.length - 1 ? '' : ', '}`
                ))}
              </Text>
            </View>

            <View style={styles.addreviewbuttoncontainer}>
              <TouchableOpacity style={styles.reviewButton} onPress={() => handlereview(order.orderId)}>
                <Text style={styles.reviewtext}>Add a Review</Text>
              </TouchableOpacity>
            </View>

          </View>
        ))}
      </View>

    </ScrollView>
  );
};


const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: "column",
    backgroundColor: "lightgrey",
  },
  header: {
    backgroundColor: "black",
    elevation: 5,
    width: "100%",
    alignItems:"center",
  },
  headertext:{
    color:"white",
    fontSize: 25,
    fontWeight: "bold",
    textAlign: "center",
    margin:5,
  },
  profilebody:{
    width:'100%',
    alignSelf:"center",
    backgroundColor: "white",
    padding: 10,
  },
  profileinfocontainer:{
    flexDirection:"row",
    justifyContent:"space-between",
    borderWidth:1,
  },
  profilephotoContainer:{
    flex:1,
    justifyContent:"center",
    alignItems:"center",
    borderRightWidth:1,
  },
  photoContainer:{
    width: "100%",
    aspectRatio:1,
    overflow:"hidden",
  },
  photo:{
    width: '100%',
    height: '100%',
  },
  photobutton:{
    backgroundColor: "#EAB308",
    paddingVertical: 2,
    paddingHorizontal:5,
    borderRadius: 5,
    margin:5,
  },
  phototext:{
    fontSize: 10,
    alignSelf:"center",
  },
  profileinfo:{
    flex:3,
    paddingHorizontal: 7,
    paddingBottom:7,
    justifyContent:"space-evenly"
  },
  nametext:{
    fontSize: 20,
    fontWeight: "bold",
    textAlign: "left",
  },
  bannertext:{
    fontSize: 15,
    textAlign: "left",
  },
  emailtext:{
    fontSize: 15,
    textAlign: "left",
  },
  profilebuttonscontainer: {
    flexDirection: "row",
    justifyContent: "space-evenly",
    borderWidth:1,
    borderTopWidth:0,    
  },
  button:{
    backgroundColor: "#EAB308",
    paddingVertical: 7,
    paddingHorizontal: 15,
    borderRadius: 10,
    marginVertical:10,
  },
  buttonText:{
    fontSize: 18,
    fontWeight: "bold",
    textAlign: "center",
  },
  ordercontainer:{
    padding:5
  },
  ordercard: {
    backgroundColor: 'white',
    width:"95%",
    alignSelf:"center",
    padding: 10,
    marginVertical:5,
    borderRadius: 10,
    elevation: 20,
    shadowColor:"black",
  },
  row1:{
    flexDirection:"row",
    justifyContent:"space-between",
    backgroundColor: "black",
  },
  row1text:{
    fontSize: 16,
    color:"white",
    padding:3,
    paddingHorizontal:7,
  },
  row2:{
    flexDirection:"row",
    justifyContent:"space-between",
  },
  row2text:{
    fontSize: 20,
    fontWeight:"bold",
    color:"black",
    padding:3,
  },
  row3:{
    flexDirection:"row",
  },
  row3text: {
    fontSize: 16,
  },
  addreviewbuttoncontainer:{
    marginTop:10,
    alignSelf:"center",
  },
  reviewButton:{
    backgroundColor: "#EAB308",
    paddingVertical: 5,
    paddingHorizontal: 10,
    borderRadius: 7,
  },
  reviewtext:{
    fontSize: 15,
    fontWeight: "bold",
    textAlign: "center",
  },
});

export default Profile;