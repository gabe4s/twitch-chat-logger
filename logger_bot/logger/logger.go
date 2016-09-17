package main

import (
	"fmt"
	"github.com/thoj/go-ircevent"
	"gopkg.in/yaml.v2"
	"io/ioutil"
	"net/http"
	"net/url"
	"time"
	"twitch-chat-logger/logger_bot/ircutils"
)

type IrcSettings struct {
	Nick     string
	User     string
	Password string
	Server   string
	Channels []string
	Port     int
	Token    string
}

func getSettings() IrcSettings {
	data, err := ioutil.ReadFile("settings.yaml")
	if err != nil {
		fmt.Printf("error reading settings.yaml: %v\n", err)
	}
	ircObj := IrcSettings{}
	err2 := yaml.Unmarshal(data, &ircObj)
	if err2 != nil {
		fmt.Printf("error un-yaml-ing settings.yaml: %v\n", err2)
	}

	return ircObj
}

func sendPostToServer(text, channel, sender string, msg_time time.Time, token string) {
	values := url.Values{}
	values.Add("sender", sender)
	values.Add("channel", channel)
	values.Add("text", text)
	values.Add("msg_time", msg_time.String())
	values.Add("token", token)
	resp, err := http.PostForm("http://localhost:8080/tcl/log", values)
	if err != nil {
		fmt.Printf("Encountered error posting to server: %v\n", err)
	}
	defer resp.Body.Close()
}

func getCallback(con *irc.Connection, token string) func(*irc.Event) {
	return func(e *irc.Event) {
		msg := e.Message()
		channel := e.Arguments[0]
		channel = channel[1:] // Remove the "#" from "#monotonetim"
		sender := e.Nick
		msg_time := time.Now()
		sendPostToServer(msg, channel, sender, msg_time, token)
	}
}

func main() {
	settings := getSettings()
	con := ircutils.GetConnection(
		settings.Server,
		settings.Port,
		settings.Nick,
		settings.User,
		settings.Password,
		settings.Channels,
	)
	con.AddCallback("PRIVMSG", getCallback(con, settings.Token))

	con.Loop()
}
