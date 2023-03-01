//This Utility will return the user details
export const getLoggedInUserDetails = (type) => {
    const userInfos = sessionStorage.getItem("Digit.citizen.userRequestObject");
    const userInfo = userInfos ? JSON.parse(userInfos) : {};
    let user = userInfo?.value;
    switch(type) {
        case "username" : {
            return user;
        }
        case "roles" : {
            return user?.info?.roles?.map((e) => e.code);
        }
        default : {
            return userInfo;
        }
    }
}