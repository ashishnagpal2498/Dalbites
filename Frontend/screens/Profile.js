import React, { useState } from "react";
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';


const userData = {
  id: 1,
  name: 'Adam Bills',
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


const Profile = () => {
  const handleChangePassword = () => {
    console.log('change password button pressed');
  };


  const handleLogout = () => {
    console.log('logout button pressed');
  };


  const handlereview = () => {
    console.log('review button pressed');
  };


  const [ratings, setRatings] = useState(Array(orderHistory.length).fill(0));


  const rateOrder = (orderIndex, rating) => {
    const newRatings = [...ratings];
    newRatings[orderIndex] = rating;
    setRatings(newRatings);
  };


  return (
    <ScrollView style={styles.container}>


      <View style={styles.header}>
        <View style={styles.extraheader}>
          <Text style={styles.headertext}>Profile</Text>
        </View>
      </View>


      <View style={styles.profilebody}>


        <View style={styles.profileinfo}>
          <Text style={styles.nametext}>Name : {userData.name}</Text>
          <Text style={styles.emailtext}>Email : {userData.email}</Text>
          <Text style={styles.bannertext}>Banner Id : {userData.banner}</Text>
        </View>


        <View style={styles.profilebuttonscontainer}>


          <TouchableOpacity style={styles.button} onPress={handleChangePassword}>
            <Text style={styles.text}>Change Password</Text>
          </TouchableOpacity>


          <TouchableOpacity style={styles.button} onPress={handleLogout}>
            <Text style={styles.text}>Logout</Text>
          </TouchableOpacity>


        </View>


      </View>


      <View style={styles.header}>
        <View style={styles.extraheader}>
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
              <TouchableOpacity style={styles.button} onPress={handlereview}>
                <Text style={styles.text}>Add a Review</Text>
              </TouchableOpacity>
            </View>


            {/* <View style={styles.reviewContainer}>


              <View style={styles.ratingBox}>
                {[1, 2, 3, 4, 5].map((star, i) => (
                  <TouchableOpacity key={i} onPress={() => rateOrder(index, star)}>
                    <Text style={[styles.stars,{ color: ratings[index] >= star ? 'gold' : 'gray' }]}> ★ </Text>
                  </TouchableOpacity>
                ))}
              </View>


              <View style={styles.descriptionBox}>


              </View>


              <View style={styles.savebuttoncontainer}>
                <TouchableOpacity style={styles.logoutbutton} onPress={handleLogout}>
                  <Text style={styles.text}>Logout</Text>
                </TouchableOpacity>
              </View>
             
            </View> */}


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
  },
  header: {
    padding: 10,
    backgroundColor: "black",
    elevation: 5,
    width: "100%",
    alignItems:"center",
    marginBottom:7,  
  },
  extraheader: {
    backgroundColor: "white",
    padding: 7,
    borderRadius: 10,
    width: "98%",
  },
  headertext:{
    color:"black",
    fontSize: 20,
    fontWeight: "bold",
    textAlign: "center",
  },
  text:{
    fontSize: 20,
    fontWeight: "bold",
    textAlign: "center",
  },
  profilebody:{
    width:'93%',
    alignSelf:"center",
    backgroundColor: "white",
    borderRadius: 10,
    elevation: 5,
    marginBottom:7,  
    padding: 7,
  },
  profileinfo:{
    paddingHorizontal: 7,
    paddingBottom:7,
  },
  nametext:{
    fontSize: 20,
    fontWeight: "bold",
    textAlign: "left",
  },
  bannertext:{
    fontSize: 15,
    fontWeight: "bold",
    textAlign: "left",
  },
  emailtext:{
    fontSize: 15,
    fontWeight: "bold",
    textAlign: "left",
  },
  profilebuttonscontainer: {
    flexDirection: "row",
    justifyContent: "space-evenly"
  },
  button:{
    backgroundColor: "#EAB308",
    paddingVertical: 7,
    paddingHorizontal: 15,
    borderRadius: 10,
  },
  ordercontainer:{


  },
  ordercard: {
    backgroundColor: 'white',
    width:'93%',
    alignSelf:"center",
    padding: 10,
    marginBottom:7,  
    borderRadius: 10,
    elevation: 5,
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
  // ratingBox:{
  //   flexDirection: "row",
  //   alignSelf: 'center',
  // },  
  // stars:{
  //   fontSize:25,
  // },
  addreviewbuttoncontainer:{
    marginTop:7,
    width:"90%",
    alignSelf:"center",
    alignItems:"center",
  },
});


export default Profile;