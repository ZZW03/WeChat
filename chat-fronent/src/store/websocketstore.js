import { defineStore } from 'pinia';


export const websocketstore = defineStore('websocket', {
    state: () => {
        return{
            socket: null,
        }
    },
    actions: {
        setSocket(socket) {
            this.socket = socket;
        },
        initializeWebSocket() {

            const socket = new WebSocket("ws://localhost:19000/ws");
            this.setSocket(socket);
        }
    }
});