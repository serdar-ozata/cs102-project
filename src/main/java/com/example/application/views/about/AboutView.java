package com.example.application.views.about;


import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.dependency.CssImport;

import java.awt.*;

@Route(value = "about")
@PageTitle("About")
@CssImport("./views/about/about-view.css")
public class AboutView extends Div {

    public AboutView() {
        addClassName("about-view");
        VerticalLayout lo = new VerticalLayout();
        lo.add(new H4("Berk Özkan\n" +
                "I was responsible for handling the heart of the project–mapbox API and OpenWeatherApp API. Initially we set our sights on a desktop application that used JavaFx and html/js; however, JFX was not able to accommodate for what we wanted to do. The biggest issue, which also prevented the progression of the project, was that JFX wasn’t able to load or fetch the mapbox API. After months or of trying to make it work I decided that it was best to switch to a web application–which could easily load the APIs. While a web based application could load the necessary tools, it also presented me with another challenge–JavaScript. So, for this project I learned JS. Most of the time the process was a lot of coding and research at the same time, and I blieve this strategy helped me a lot when learning how to manage JS, MapBox, and OpenWeatherApp. I really feel like I have expanded my capabilities in this project. More important than code or syntax, I learned how to learn new computer languages, how to to implement external technologies, and how to go about combining two unrelated languages. The hardest and most troubling part was learning the fundamentals of the technologies I used. Surprisingly, combining the code was not as challenging as I initially thought–updating our code 4-5 times a day probably helped though. Finally, if I were to re-do this project I would have spent less time on trying to figure out something that doesn’t work. I definitely am very proud of what we have accomplished.\n" +
                "\n" +
                "\n" +
                "Anıl Altuncu \n" +
                "My side of this work is part of creating database. Eren and I worked together. At first, I searched how can we store datas. This part was hard to me because this was my first experiment. Eren told me that we can use sql in order to store data and we started to watch some tutorials and tried to learn what is sql and how can we connect with java code. I learned a little JDBC and SQLite in this project. At the begining of the coding part we created classes basically which we implement in uml diagram beforehand. Next we divide the methods with Eren. If I had to start over again I would not go fast while learning new things because I felt like I have to study more while coding. Most difficult part was finding out what to do. It took much time than I expected. I think my coding part was short. However, finding the true technology for project took time, If Eren had not found, I would never have found.\n" +
                "\n" +
                "Eren Duran\n" +
                "At the very beginning, I was clueless because I didn't have any prior knowledge about databases and how to create one, or even I didn't even know how does it work. Then I looked at the internet and got an idea about using hashtables and serializing those hashtables to a file to have a working thing that holds data. After some time and advice, we concluded that this was not going to work. So I started to search for a solution again, which was the worst part of this project. Figuring out what technology to use was the most stressful thing ever. At first, when ı did a small search, I came upon something called JPA, then I tried to learn it, but this attempt was unsuccessful because when I watched some videos about JPA, I didn't understand anything, and this made the whole situation worst. After some more digging, I came upon a thing called Sqlite and JDBC. After learning about these for 8 hours, I was in a vast relief phase because Anıl and I figured out how to do this project, and after all, it was a smooth course for us.  I am proud of what we have achieved. And if we ever face a task like this, my first goal will be learning the fundamentals of everything, like starting from JPA was a mistake, in my opinion.\n" +
                "\n" +
                "Serdar Özata\n" +
                "At first we were going to make a desktop app and I was responsible for its UI design part. For that reason, since it was going to be a desktop app, I’ve started learning javafx and experimenting with it. Then I’ve made a simple app that allows the user to draw any shape the user wants. This was also a component that I was supposed to make in our desktop app. So, I was slowly learning and making javafx components for our app. However, Berk said that there was no way to use mapbox api in a desktop app which was crucial in our program. Therefore, we decided to make a web app from scratch. It wouldn’t be a problem if we knew how to make a web app using java and we had more time. Later we decided to make it using vaadin and spring boot. However, we had no idea how to make a web app using vaadin. Since I was in the UI part. I had lots of pressure to learn how to make web apps. I tried to find ways to solve our problems. Connecting mapbox to vaadin, logging in-logging out systems, layout, search bar, tab connections. I did all of them but just making a search bar and its options took nearly 7 hours. What I mean is I solved problems but it wasn’t so enjoyable. I felt that I had a lot to do in a short period of time. I’ve been working more than 12 hours in a day last week. But in the end I’m proud of what we’ve accomplished.\n" +
                "\n" +
                "\n" +
                "Emirhan Büyükkonuklu\n" +
                "I was part of constructing the UI. When we were at the design stage, we planned the app to be desktop-based. But after some hardships, we decided to go with web-based. This was a bit late decision for me and Serdar. Because we had to change our UI framework, JavaFX( the framework we were going to use to construct the UI), and swap to Vaadin, a UI framework for web apps. We were scared at first but Vaadin was a savior. We didn't have to know HTML, CSS, or JavaScript to construct UI but just Java.  My dislikes about the project were late-hour studies. We started coding a bit late so we had to limit our sleep. But in the end, I can say that I do not regret it. If I had to start over, I would learn more about Eren and Anıl's work by watching them or maybe helping them. I was only minding my own business and if I needed database's methods I would only ask them. That was the whole relationship between me and the database. Vaadin offers much about it. And it would benefit the look and feel of the UI heavily. I would also  try to configure gitHub and use it more often than we did in our implementation stage. Overall I am really proud of what we have achieved as a group. In such a limited time we made something great. Not only I learned quite a bit about hard skills like web development in Java, but I also sensed that I enhanced my soft skills. I would also learn css to integrate with vaadin. \n" +
                "\n" +
                "Over-all group thoughts\n" +
                "\n" +
                "One of the most important things we learned was group management and work distribution. At first we didn’t know how to best split the work; however, through trial and error we managed to learn how to. We also learned how to learn new technologies. As a group we definitely agree that the evaluation criteria was good. If no one is allowed to get a 10/10 then what is the point of having a 10? Finally, the next time we tackle a project like this we will spend more time on class design; we struggled on this step because we learned what and how we could do things as we progressed. So, we researched as we did. Yes, we are very proud of what we have accomplished.  \n"));
        add(lo);
    }


}
