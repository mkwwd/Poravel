import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

import { userConfirm, findUser, tokenRegeneration, logout, userDelete } from "../api/user";
import { httpStatusCode } from "../util/http-status";
import errorImage from "@/assets/img/sea.png";

export const useMemberStore = defineStore(
    "memberStore",
    () => {
        const router = useRouter();
        const isLogin = ref(false);
        const isLoginError = ref(false);
        const userInfo = ref(null);
        const isValidToken = ref(false);

        const userLogin = async (loginUser) => {
            await userConfirm(
                loginUser,
                (response) => {
                    if (response.status === httpStatusCode.CREATE) {
                        console.log("***************************11")
                        console.log(response.status)
                        let { data } = response;
                        console.log("로그인 되었나봐");
                        let accessToken = data["access_token"];
                        let refreshToken = data["refresh_token"];
                        console.log("accessToken", accessToken);
                        console.log("refreshToken", refreshToken);
                        isLogin.value = true;
                        isLoginError.value = false;
                        isValidToken.value = true;
                        sessionStorage.setItem("accessToken", accessToken);
                        sessionStorage.setItem("refreshToken", refreshToken);
                        console.log("sessiontStorage에 담았다", isLogin.value);
                    } else {
                        console.log("**************22")
                        console.log(response.status)
                        console.log("로그인 실패했다");
                        isLogin.value = false;
                        isLoginError.value = true;
                        isValidToken.value = false;
                    }
                },
                (error) => {
                    console.error(error);
                }
            );
        };

        const getUserInfo = () => {
            // let decodeToken = jwtDecode(token);
            findUser(
                (response) => {
                    if (response.status === httpStatusCode.OK) {
                        userInfo.value = response.data.userInfo;

                        console.log("3. getUserInfo data >> ", response.data);
                    } else {
                        console.log("유저 정보 없음!!!!");
                    }
                },
                async (error) => {
                    console.error("getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ", error.response.status);
                    isValidToken.value = false;

                    await tokenRegenerate();
                }
            );
        };

        const tokenRegenerate = async () => {
            console.log("토큰 재발급 >> 기존 토큰 정보 : {}", sessionStorage.getItem("accessToken"));
            await tokenRegeneration(
                JSON.stringify(userInfo.value),
                (response) => {
                    if (response.status === httpStatusCode.CREATE) {
                        let accessToken = response.data["access-token"];
                        console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
                        sessionStorage.setItem("accessToken", accessToken);
                        isValidToken.value = true;
                    }
                },
                async (error) => {
                    // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
                    if (error.response.status === httpStatusCode.UNAUTHORIZED) {
                        console.log("갱신 실패");
                        // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
                        await logout(
                            (response) => {
                                if (response.status === httpStatusCode.OK) {
                                    console.log("리프레시 토큰 제거 성공");
                                } else {
                                    console.log("리프레시 토큰 제거 실패");
                                }
                                alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
                                isLogin.value = false;
                                userInfo.value = null;
                                isValidToken.value = false;
                                router.push({ name: "user-login" });
                            },
                            (error) => {
                                console.error(error);
                                isLogin.value = false;
                                userInfo.value = null;
                            }
                        );
                    }
                }
            );
        };

        const userLogout = async () => {
            await logout(
                (response) => {
                    if (response.status === httpStatusCode.OK) {
                        isLogin.value = false;
                        userInfo.value = null;
                        isValidToken.value = false;
                        sessionStorage.removeItem("accessToken");
                        sessionStorage.removeItem("refreshToken");
                    } else {
                        console.error("유저 정보 없음!!!!");
                    }
                },
                (error) => {
                    console.log(error);
                }
            );
        };

        const replaceImg = (e) => {
            console.log("replace");
            console.log(e);
            e.target.src = errorImage;
        };

        const deleteUser = async () => {
            await userDelete(
                (response) => {
                    if (response.status === httpStatusCode.OK) {
                        console.log("탈퇴완료");
                        isLogin.value = false;
                        userInfo.value = null;
                        isValidToken.value = false;
                        sessionStorage.removeItem("accessToken");
                        sessionStorage.removeItem("refreshToken");
                    } else {
                        console.error("틸퇴실패");
                    }
                },
                (error) => {
                    console.log(error);
                }
            );
        };

        return {
            isLogin,
            isLoginError,
            userInfo,
            isValidToken,
            userLogin,
            getUserInfo,
            tokenRegenerate,
            userLogout,
            replaceImg,
            deleteUser,
        };
    },
    { persist: { storage: sessionStorage } }
);
