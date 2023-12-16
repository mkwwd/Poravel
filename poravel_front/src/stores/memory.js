import { ref } from "vue";
import { defineStore } from "pinia";

export const useMemoryStore = defineStore("memoryStore", () => {
    const now = ref("");

    const bestList = ref([
        {
            memory_pk: "1",
            user_name: "apple",
            location: "여의도 한강공원1",
            memory_date: "2023.02.01",
            memory_hit: "3",
            comment: "너무 좋았다",
            imgUrl: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/dXAhQuT.jpg",
        },
        {
            memory_pk: "2",
            user_name: "orange",
            location: "여의도 한강공원2",
            memory_date: "2023.02.01",
            memory_hit: "3",
            comment: "너무 좋았다",
            imgUrl: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/LZkivxR.jpg",
        },
        {
            memory_pk: "3",
            user_name: "banana",
            location: "여의도 한강공원3",
            memory_date: "2023.02.01",
            memory_hit: "3",
            comment: "너무 좋았다",
            imgUrl: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/hqcMtrF.jpg",
        },
        {
            memory_pk: "4",
            user_name: "peach",
            location: "여의도 한강공원4",
            memory_date: "2023.02.01",
            memory_hit: "3",
            comment: "너무 좋았다",
            imgUrl: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/l867sBU.jpg",
        },
        {
            memory_pk: "5",
            user_name: "cherry",
            location: "여의도 한강공원5",
            memory_date: "2023.02.01",
            memory_hit: "3",
            comment: "너무 좋았다",
            imgUrl: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/7cQCk5I.jpg",
        },
    ]);

    const memoryList = ref([
        {
            memory_pk: "6",
            user_name: "apple",
            location: "여의도 한강공원1",
            memory_date: "2023.02.01",
            memory_hit: "3",
            comment: "너무 좋았다",
            imgUrl: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/dXAhQuT.jpg",
        },
        {
            memory_pk: "7",
            user_name: "orange",
            location: "여의도 한강공원2",
            memory_date: "2023.02.01",
            memory_hit: "3",
            comment: "너무 좋았다",
            imgUrl: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/LZkivxR.jpg",
        },
        {
            memory_pk: "8",
            user_name: "banana",
            location: "여의도 한강공원3",
            memory_date: "2023.02.01",
            memory_hit: "3",
            comment: "너무 좋았다",
            imgUrl: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/hqcMtrF.jpg",
        },
        {
            memory_pk: "9",
            user_name: "peach",
            location: "여의도 한강공원4",
            memory_date: "2023.02.01",
            memory_hit: "3",
            comment: "너무 좋았다",
            imgUrl: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/l867sBU.jpg",
        },
        {
            memory_pk: "9",
            user_name: "cherry",
            location: "여의도 한강공원5",
            memory_date: "2023.02.01",
            memory_hit: "3",
            comment: "너무 좋았다",
            imgUrl: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/7cQCk5I.jpg",
        },
        {
            memory_pk: "10",
            user_name: "apple",
            location: "여의도 한강공원1",
            memory_date: "2023.02.01",
            memory_hit: "3",
            comment: "너무 좋았다",
            imgUrl: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/dXAhQuT.jpg",
        },
        {
            memory_pk: "11",
            user_name: "orange",
            location: "여의도 한강공원2",
            memory_date: "2023.02.01",
            memory_hit: "3",
            comment: "너무 좋았다",
            imgUrl: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/LZkivxR.jpg",
        },
        {
            memory_pk: "12",
            user_name: "banana",
            location: "여의도 한강공원3",
            memory_date: "2023.02.01",
            memory_hit: "3",
            comment: "너무 좋았다",
            imgUrl: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/hqcMtrF.jpg",
        },
        {
            memory_pk: "13",
            user_name: "peach",
            location: "여의도 한강공원4",
            memory_date: "2023.02.01",
            memory_hit: "3",
            comment: "너무 좋았다",
            imgUrl: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/l867sBU.jpg",
        },
        {
            memory_pk: "14",
            user_name: "cherry",
            location: "여의도 한강공원5",
            memory_date: "2023.02.01",
            memory_hit: "3",
            comment: "너무 좋았다",
            imgUrl: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/7cQCk5I.jpg",
        },
    ]);

    return {
        bestList,
        memoryList,
    };
});
