var MongoClient = require('mongodb').MongoClient;
var express = require("express");
var fs = require("fs");
var bodyParser = require('body-parser');
var multer  = require('multer');
var request =require('request');
var app = express();

// static files
app.use(express.static('public'));

app.use(express.static(__dirname + '/up')); //静态文件目录

app.use(multer({ dest: '/tmp/'}).array('image'));


// use bodyParser-text
app.use(bodyParser.text({type:"txt",limit:'50mb'}));
app.use(bodyParser.json({limit:'50mb'}));
app.use(bodyParser.urlencoded({limit:'50mb',extended:true}));

function Create(collection, object) {
    collection.insert(object, function (err, result) {

    });
}

//登录
app.get("/login", function (req, res) {
    console.log('request.url:',req.url);  //打印请求的地址
    res.setHeader("Access-Control-Allow-Origin", "*");//跨域访问
    console.log(req.query); //打印参数数据

    MongoClient.connect("mongodb://localhost", function (err, db) {
        var MyDB = db.db("movieDB");

        MyDB.collection("users", function (err, users) {
            users.findOne({username:req.query.username}, function (err, item) {
                if (!item) {
                    db.close();
                    var obj = { value: "false", text:"用户不存在"};
                    var myJSON = JSON.stringify(obj);
                    res.end(myJSON);
                } else {
                    console.log('find success!');

                    if (item.password === req.query.password) {//登录成功
                        db.close();
                        var obj = { value: "true", text:item.photo};
                        var myJSON = JSON.stringify(obj);
                        res.end(myJSON);
                        calculateTypicalUserLike(item.sex,item.year,item.study);//统计该类用户喜好
                    }
                    else {
                        db.close();
                        var obj = { value: "false", text:"密码错误"};
                        var myJSON = JSON.stringify(obj);
                        res.end(myJSON);
                    }
                }
            });
        });
    });
});

//注册
app.get("/register", function (req, res) {
    console.log('request.url:',req.url);  //打印请求的地址
    res.setHeader("Access-Control-Allow-Origin", "*");//跨域访问
    console.log(req.query); //打印参数数据

    MongoClient.connect("mongodb://localhost", function (err, db) {
        var MyDB = db.db("movieDB");

        MyDB.collection("users", function (err, users) {
            users.findOne({username:req.query.username}, function (err, item) {
                if (!item) {
                    MyDB.createCollection("users", function (err, users) {
                        Create(users, {username: req.query.username, password: req.query.password,photo:req.query.photo ,sex: req.query.sex,year: req.query.year,study: req.query.study});
                        calculateUserLike(req.query.username);
                        db.close();
                        var obj = { value: "true", text:"注册成功"};
                        var myJSON = JSON.stringify(obj);
                        res.end(myJSON);
                    });
                } else {
                    db.close();
                    var obj = { value: "false", text:"用户名已经存在"};
                    var myJSON = JSON.stringify(obj);
                    res.end(myJSON);
                }
            });
        });
    });
});

//收藏
app.get("/collect", function (req, res) {
    console.log('request.url:',req.url);  //打印请求的地址
    res.setHeader("Access-Control-Allow-Origin", "*");//跨域访问
    console.log(req.query); //打印参数数据

    MongoClient.connect("mongodb://localhost", function (err, db) {
        var MyDB = db.db("movieDB");

        MyDB.collection("collect", function (err, result) {
            result.findOne({username:req.query.username,movieId:req.query.movieId}, function (err, item) {
                if (!item) {
                    //插入收藏数据
                    var insertStr = {username: req.query.username, movieId: req.query.movieId,movieTitle:req.query.movieTitle ,time:req.query.time,movieLanguage:req.query.movieLanguage,movieYear:req.query.movieYear,movieType:req.query.movieType,movieDirector:req.query.movieDirector};
                    MyDB.collection("collect").insertOne(insertStr,function(err, result) {
                        if (err) throw err;
                        console.log(result);
                        var obj = { value: "true", text:"收藏成功"};
                        var myJSON = JSON.stringify(obj);
                        res.end(myJSON);
                        db.close();
                        calculateUserLike(req.query.username);
                    });
                } else {
                    db.close();
                    var obj = { value: "false", text:"该电影已经被收藏了"};
                    var myJSON = JSON.stringify(obj);
                    res.end(myJSON);
                }
            });
        });
    });
});

//取消收藏
app.get("/cancel_collect", function (req, res) {
    console.log('request.url:',req.url);  //打印请求的地址
    res.setHeader("Access-Control-Allow-Origin", "*");//跨域访问
    console.log(req.query); //打印参数数据

    MongoClient.connect("mongodb://localhost", function (err, db) {
        var MyDB = db.db("movieDB");
        var whereStr = {"username":req.query.username, "movieId": req.query.movieId};  // 查询条件
        MyDB.collection("collect").findOne(whereStr, function(err, result){
            if (!result){//不存在
                var obj = { value: "false", text:"该收藏记录不存在"};
                var myJSON = JSON.stringify(obj);
                res.end(myJSON);
            }
            else{
                MyDB.collection("collect").deleteOne(whereStr, function(err, result) {
                    if (err) {
                        var obj = { value: "false", text:err};
                        var myJSON = JSON.stringify(obj);
                        res.end(myJSON);
                    }
                    else {
                        var obj = { value: "true", text:"取消收藏成功"};
                        var myJSON = JSON.stringify(obj);
                        res.end(myJSON);
                        calculateUserLike(req.query.username);
                    }
                    db.close();
                });
            }
        });
    });
});

//判断收藏与否
app.get("/collect_or_not", function (req, res) {
    console.log('request.url:',req.url);  //打印请求的地址
    res.setHeader("Access-Control-Allow-Origin", "*");//跨域访问
    console.log(req.query); //打印参数数据

    MongoClient.connect("mongodb://localhost", function (err, db) {
        var MyDB = db.db("movieDB");
        var whereStr = {"username":req.query.username, "movieId": req.query.movieId};  // 查询条件
        MyDB.collection("collect").findOne(whereStr, function(err, result){
            if (!result){//不存在
                var obj = { value: "false", text:"该电影未收藏"};
                var myJSON = JSON.stringify(obj);
                res.end(myJSON);
            }
            else{
                var obj = { value: "true", text:"该电影已收藏"};
                var myJSON = JSON.stringify(obj);
                res.end(myJSON);
            }
        });
    });
});


//个人收藏列表
app.get("/collect_display", function (req, res) {
    console.log('request.url:',req.url);  //打印请求的地址
    res.setHeader("Access-Control-Allow-Origin", "*");//跨域访问
    console.log(req.query); //打印参数数据

    MongoClient.connect("mongodb://localhost", function (err, db) {
        var MyDB = db.db("movieDB");
        var whereStr = {"username":req.query.username};  // 查询条件
        var displaySet={username:1,movieId:1,movieTitle:1,time:1};//显示列
        MyDB.collection("collect").find(whereStr,displaySet).toArray(function(err,result){
            if (err) throw err;
            console.log(result);
            var obj={subjects: result};
            var myJSON = JSON.stringify(obj);
            res.end(myJSON);
            db.close();
        });
    });
});

//添加浏览记录
app.get("/add_history", function (req, res) {
    console.log('request.url:',req.url);  //打印请求的地址
    res.setHeader("Access-Control-Allow-Origin", "*");//跨域访问
    console.log(req.query); //打印参数数据

    MongoClient.connect("mongodb://localhost", function (err, db) {
        var MyDB = db.db("movieDB");

        MyDB.collection("history", function (err, result) {
            result.findOne({username:req.query.username,movieId:req.query.movieId}, function (err, item) {
                if (!item) {
                    var insertStr = {username: req.query.username, movieId: req.query.movieId,movieTitle:req.query.movieTitle ,time:req.query.time};
                    MyDB.collection("history").insertOne(insertStr,function(err, result) {
                        if (err) throw err;
                        console.log(result);
                        db.close();
                        var obj = { value: "true", text:"添加浏览记录成功"};
                        var myJSON = JSON.stringify(obj);
                        res.end(myJSON);
                    });
                } else {
                    var whereStr = {"username":req.query.username,"movieId":req.query.movieId};  // 查询条件
                    var updateStr = {$set: { "time" : req.query.time }};//查询条件
                    MyDB.collection("history").updateOne(whereStr, updateStr, function(err, result) {
                        if (err) throw err;
                        console.log(result);
                        var obj = { value: "true", text:"更改浏览记录成功"};
                        var myJSON = JSON.stringify(obj);
                        res.end(myJSON);
                        db.close();
                    });
                }
            });
        });
    });
});

//删除浏览记录
app.get("/delete_history", function (req, res) {
    console.log('request.url:',req.url);  //打印请求的地址
    res.setHeader("Access-Control-Allow-Origin", "*");//跨域访问
    console.log(req.query); //打印参数数据

    MongoClient.connect("mongodb://localhost", function (err, db) {
        var MyDB = db.db("movieDB");
        var whereStr = {"username":req.query.username, "movieId": req.query.movieId};  // 查询条件
        MyDB.collection("history").findOne(whereStr, function(err, result){
            if (!result){//不存在
                var obj = { value: "false", text:"该浏览记录不存在"};
                var myJSON = JSON.stringify(obj);
                res.end(myJSON);
            }
            else{
                MyDB.collection("history").deleteOne(whereStr, function(err, result) {
                    if (err) {
                        var obj = { value: "false", text:err};
                        var myJSON = JSON.stringify(obj);
                        res.end(myJSON);
                    }
                    else {
                        var obj = { value: "true", text:"删除浏览记录成功"};
                        var myJSON = JSON.stringify(obj);
                        res.end(myJSON);
                    }
                    db.close();
                });
            }
        });
    });
});

//个人浏览记录列表
app.get("/history_display", function (req, res) {
    console.log('request.url:',req.url);  //打印请求的地址
    res.setHeader("Access-Control-Allow-Origin", "*");//跨域访问
    console.log(req.query); //打印参数数据

    MongoClient.connect("mongodb://localhost", function (err, db) {
        var MyDB = db.db("movieDB");
        var whereStr = {"username":req.query.username};  // 查询条件
        var displaySet={username:1,movieId:1,movieTitle:1,time:1};//显示列
        MyDB.collection("history").find(whereStr,displaySet).toArray(function(err,result){
            if (err) throw err;
            console.log(result);
            var obj={subjects: result};
            var myJSON = JSON.stringify(obj);
            res.end(myJSON);
            db.close();
        });
    });
});

//个人喜好电影推荐列表
app.get("/guess_you_like", function (req, res) {
    console.log('request.url:',req.url);  //打印请求的地址
    res.setHeader("Access-Control-Allow-Origin", "*");//跨域访问
    console.log(req.query); //打印参数数据

    MongoClient.connect("mongodb://localhost", function (err, db) {
        var MyDB = db.db("movieDB");
        var whereStr = {"username":req.query.username};  // 查询条件
        MyDB.collection("users").findOne(whereStr,function(err,userData){
            if (err) throw err;
            whereStr = {"sex":userData.sex,"year":userData.year,"study":userData.study};
            MyDB.collection("typicaluser").findOne(whereStr,function (err,typicalUser) {//根据同类用户推荐

            var displayStr={_id:0,movieId:1,movieTitle:1,movieType:1,movieDirector:1,movieYear:1,movieLanguage:1};
            MyDB.collection("moviedata").find({},displayStr).toArray(function(err,result){//基于收藏内容的推荐
                if (err) throw err;
                var score=0;
                var typicalUserScore=0;
                var movieList=[];
                var typicalUserMovieList=[];
                var typeScore=5;
                var languageScore=3;
                var yearScore=2;
                console.log("111");
                for(var i=0;i<result.length;i++){
                    score =0;
                    typicalUserScore=0;
                    for(var j=0;j<userData.userLike.typeLike.length;j++){//计算电影类型得分
                        if(result[i].movieType.indexOf(userData.userLike.typeLike[j].movieType)!==-1){
                            score=score+typeScore*userData.userLike.typeLike[j].movieTypeCount;
                            typicalUserScore=typicalUserScore+typeScore*typicalUser.userLike.typeLike[j].movieTypeCount;
                        }
                    }
                    for(var j=0;j<userData.userLike.languageLike.length;j++){
                        if(result[i].movieLanguage===userData.userLike.languageLike[j].movieLanguage){
                            score=score+languageScore*userData.userLike.languageLike[j].movieLanguageCount;
                            typicalUserScore=typicalUserScore+languageScore*typicalUser.userLike.languageLike[j].movieLanguageCount;
                            break;
                        }
                    }
                    var yearInt = parseInt(result[i].movieYear);
                    for(var j=0;j<userData.userLike.yearLike.length;j++){
                        if(yearInt>=userData.userLike.yearLike[j].movieYear.start&&yearInt<=userData.userLike.yearLike[j].movieYear.end){
                            score=score+yearScore*userData.userLike.yearLike[j].movieYearCount;
                            typicalUserScore=typicalUserScore+yearScore*typicalUser.userLike.yearLike[j].movieYearCount;
                            break;
                        }
                    }

                    if(movieList.length<20){
                        movieList.push({"num":i,"score":score});
                        typicalUserMovieList.push({"num":i,"score":typicalUserScore});
                    }
                    else {
                        var limit={"num":-1,"score":100};//根据用户收藏内容推荐
                        var limitPos;
                        for(var k=0;k<movieList.length;k++){//找出最小值
                            if(movieList[k].score<limit.score)
                            {
                                limit=movieList[k];
                                limitPos=k;
                            }
                        }
                        if(score>limit.score){//大于待选列表最小值
                            movieList[limitPos]={"num":i,"score":score};
                        }
                        var typicalUserLimit={"num":-1,"score":100};//根据同类用户收藏类型推荐
                        var typicalUserLimitPos;
                        for(var k=0;k<typicalUserMovieList.length;k++){//找出最小值
                            if(typicalUserMovieList[k].score<typicalUserLimit.score)
                            {
                                typicalUserLimit=typicalUserMovieList[k];
                                typicalUserLimitPos=k;
                            }
                        }
                        if(typicalUserScore>typicalUserLimit.score){//大于待选列表最小值
                            typicalUserMovieList[typicalUserLimitPos]={"num":i,"score":typicalUserScore};
                        }

                    }
                }
                var randomNum;
                var finalList=[];
                for(var i=0;i<5;i++){//加入根据收藏列表推荐
                    randomNum = Math.random()*movieList.length ;
                    randomNum = parseInt(randomNum);
                    result[movieList[randomNum].num].reason="根据您的收藏列表推荐";
                    finalList.push(result[movieList[randomNum].num]);
                    //console.log(result[movieList[randomNum].num].movieTitle);
                    movieList[randomNum]=movieList[movieList.length-1];
                    movieList.length=movieList.length-1;
                }
                for(var i=0;i<5;i++){//加入根据同类用户口味推荐
                    randomNum = Math.random()*typicalUserMovieList.length ;
                    randomNum = parseInt(randomNum);
                    result[typicalUserMovieList[randomNum].num].reason="小伙伴们都在看";
                    finalList.push(result[typicalUserMovieList[randomNum].num]);
                    //console.log(result[movieList[randomNum].num].movieTitle);
                    typicalUserMovieList[randomNum]=typicalUserMovieList[typicalUserMovieList.length-1];
                    typicalUserMovieList.length=typicalUserMovieList.length-1;
                }

                var whereStr = {"username":req.query.username};  // 查询条件
                MyDB.collection("collect").find(whereStr).toArray( function(err, userCollect) {//加入根据导演推荐
                    if (err) throw err;
                    var byDirectorList=[];
                    var haveInFinalList;
                    for(var i=0;i<userCollect.length;i++){
                        for(var j=0;j<result.length;j++){
                            if(result[j].movieDirector===userCollect[i].movieDirector&&result[j].movieId!==userCollect[i].movieId){
                                haveInFinalList=0;
                                for(var k=0;k<finalList.length;k++){
                                    if (result[j].movieId===finalList[k].movieId){
                                        haveInFinalList=1;
                                        break;
                                    }
                                }
                                for(var m=0;m<userCollect.length;m++){
                                    if (result[j].movieId===userCollect[m].movieId){
                                        haveInFinalList=1;
                                        break;
                                    }
                                }
                                if(haveInFinalList===0){
                                    byDirectorList.push(result[j]);
                                }
                            }
                        }
                    }
                    if(byDirectorList.length!==0){//随机选择观看过的导演的其他作品
                        var randomNum = Math.random()*byDirectorList.length ;
                        randomNum = parseInt(randomNum);
                        byDirectorList[randomNum].reason=byDirectorList[randomNum].movieDirector+"导演的其他作品";
                        finalList.push(byDirectorList[randomNum]);
                        var obj = { "subjects": finalList};
                        var myJSON = JSON.stringify(obj);
                        res.end(myJSON);
                        db.close();
                    }
                    else{
                        var obj = { "subjects": finalList};
                        var myJSON = JSON.stringify(obj);
                        res.end(myJSON);
                        db.close();
                    }

                });
            });
            });
        });
    });
});

//获取电影统计数据
app.get("/get_movie_count", function (req, res) {
    console.log('request.url:',req.url);  //打印请求的地址
    res.setHeader("Access-Control-Allow-Origin", "*");//跨域访问
    console.log(req.query); //打印参数数据
    MongoClient.connect("mongodb://localhost", function (err, db) {
        var MyDB = db.db("movieDB");
        var i=0;
        var movieType=["剧情","爱情","奇幻","悬疑","恐怖","惊悚","动作","喜剧","科幻","古装","武侠","家庭","传记","运动","动画","音乐","犯罪","冒险","战争","歌舞","儿童","同性"];
        var movieTypeCount=[];
        for( i=0;i<movieType.length;i++){
            movieTypeCount[i]=0;
        }
        var movieLanguage=["汉语普通话","英语","日语","韩语","粤语"];
        var movieLanguageCount=[];
        for( i=0;i<movieLanguage.length;i++){
            movieLanguageCount[i]=0;
        }
        var movieYear=[{"start":1800,"end":1960},{"start":1961,"end":1980},{"start":1981,"end":1990},{"start":1991,"end":2000},{"start":2001,"end":2010},{"start":2011,"end":2015},{"start":2016,"end":2017},{"start":2018,"end":2019}];
        var movieYearCount=[];
        for( i=0;i<movieYear.length;i++){
            movieYearCount[i]=0;
        }
        MyDB.collection("collect").find({}).toArray(function(err,result){
            if (err) throw err;
            console.log(result);
            for(var i=0;i<result.length;i++){
                var j=0;
                for( j=0;j<movieType.length;j++){
                    if(result[i].movieType.indexOf(movieType[j])!== -1){
                        movieTypeCount[j]=movieTypeCount[j]+1;
                    }
                }
                for( j=0;j<movieLanguage.length;j++){
                    if(result[i].movieLanguage===movieLanguage[j]){
                        movieLanguageCount[j]=movieLanguageCount[j]+1;
                    }
                }
                var yearInt = parseInt(result[i].movieYear);
                for( j=0;j<movieYear.length;j++){
                    if(yearInt<=movieYear[j].end&&yearInt>=movieYear[j].start){
                        movieYearCount[j]=movieYearCount[j]+1;
                    }
                }
            }

            var totalType=0;
            for(i=0;i<movieType.length;i++) {
                totalType=totalType+movieTypeCount[i];
            }
            for(i=0;i<movieType.length;i++) {
                movieTypeCount[i]=movieTypeCount[i]/totalType;
                movieTypeCount[i]=movieTypeCount[i]*1000;
                movieTypeCount[i]=Math.round(movieTypeCount[i]);
                movieTypeCount[i]=movieTypeCount[i]/10;
            }
            for(i=0;i<movieLanguage.length;i++) {
                movieLanguageCount[i]=movieLanguageCount[i]/result.length;
                movieLanguageCount[i]=movieLanguageCount[i]*1000;
                movieLanguageCount[i]=Math.round(movieLanguageCount[i]);
                movieLanguageCount[i]= movieLanguageCount[i]/10;
            }
            for(i=0;i<movieYear.length;i++) {
                movieYearCount[i]=movieYearCount[i]/result.length;
                movieYearCount[i]=movieYearCount[i]*1000;
                movieYearCount[i]=Math.round(movieYearCount[i]);
                movieYearCount[i]=movieYearCount[i]/10;
            }

            for(i=0;i<movieType.length-1;i++){//冒泡排序
                for(j=0;j<movieType.length-i-1;j++){
                    if(movieTypeCount[j]<movieTypeCount[j+1]){
                        var swap=movieTypeCount[j];
                        movieTypeCount[j]=movieTypeCount[j+1];
                        movieTypeCount[j+1]=swap;
                        swap=movieType[j];
                        movieType[j]=movieType[j+1];
                        movieType[j+1]=swap;
                    }
                }
            }
            for(i=0;i<movieYear.length-1;i++){//冒泡排序
                for(j=0;j<movieYear.length-i-1;j++){
                    if(movieYearCount[j]<movieYearCount[j+1]){
                        var swap=movieYearCount[j];
                        movieYearCount[j]=movieYearCount[j+1];
                        movieYearCount[j+1]=swap;
                        swap=movieYear[j];
                        movieYear[j]=movieYear[j+1];
                        movieYear[j+1]=swap;
                    }
                }
            }

            var userTypeLike=[];
            var score=100;
            for(i=0;i<5;i++){
                userTypeLike.push({"name":movieType[i],"count":movieTypeCount[i]})
                score=score-movieTypeCount[i];
            }
            userTypeLike.push({"name":"其他","count":score});

            var userLanguageLike=[];
            for(i=0;i<movieLanguage.length;i++){
                userLanguageLike.push({"name":movieLanguage[i],"count":movieLanguageCount[i]})
            }

            var userYearLike=[];
            score=100;
            for(i=0;i<4;i++){
                userYearLike.push({"name":movieYear[i].start+"-"+movieYear[i].end,"count":movieYearCount[i]})
                score=score-movieYearCount[i];
            }
            userYearLike.push({"name":"其他","count":score});

            var obj={"typeCount":userTypeLike,"languageCount":userLanguageLike,"yearCount":userYearLike};
            var myJSON = JSON.stringify(obj);
            res.end(myJSON);
            db.close();
        });
    });
});

//获取用户数据统计
app.get("/get_user_count", function (req, res) {
    console.log('request.url:',req.url);  //打印请求的地址
    res.setHeader("Access-Control-Allow-Origin", "*");//跨域访问
    console.log(req.query); //打印参数数据

    MongoClient.connect("mongodb://localhost", function (err, db) {
        var MyDB = db.db("movieDB");
       var i=0;
        var manYear=["0-15","16-20","21-25","26-30","31-35","36-40","41-50","50+"];
        var manYearCount=[];
        for( i=0;i<manYear.length;i++){
            manYearCount[i]=0;
        }

        var manStudy=["高中及以下","专科","本科","研究生及以上"];
        var manStudyCount=[];
        for( i=0;i<manStudy.length;i++){
            manStudyCount[i]=0;
        }

        var womanYear=["0-15","16-20","21-25","26-30","31-35","36-40","41-50","50+"];
        var womanYearCount=[];
        for( i=0;i<womanYear.length;i++){
            womanYearCount[i]=0;
        }

        var womanStudy=["高中及以下","专科","本科","研究生及以上"];
        var womanStudyCount=[];
        for( i=0;i<womanStudy.length;i++){
            womanStudyCount[i]=0;
        }

        var whereStr = {"sex":"男"};  // 查询条件
        MyDB.collection("users").find(whereStr).toArray(function(err, manResult) {
            if (err) throw err;
            for(var i=0;i<manResult.length;i++){
                for(var j=0;j<manYear.length;j++){
                    if(manResult[i].year===manYear[j]){
                        manYearCount[j]=manYearCount[j]+1;
                        break;
                    }
                }
                for(var j=0;j<manStudy.length;j++){
                    if(manResult[i].study===manStudy[j]){
                        manStudyCount[j]=manStudyCount[j]+1;
                        break;
                    }
                }
            }
            var whereStr = {"sex":"女"};  // 查询条件
            MyDB.collection("users").find(whereStr).toArray(function(err, womanResult) {
                if (err) throw err;
                for(var i=0;i<womanResult.length;i++){
                    for(var j=0;j<womanYear.length;j++){
                        if(womanResult[i].year===womanYear[j]){
                            womanYearCount[j]=womanYearCount[j]+1;
                            break;
                        }
                    }
                    for(var j=0;j<womanStudy.length;j++){
                        if(womanResult[i].study===womanStudy[j]){
                            womanStudyCount[j]=womanStudyCount[j]+1;
                            break;
                        }
                    }
                }
                var womanYearList=[];
                for(var i=0;i<womanYear.length;i++)
                    womanYearList.push({"name": womanYear[i],"count":womanYearCount[i]});
                var manYearList=[];
                for(var i=0;i<manYear.length;i++)
                    manYearList.push({"name": manYear[i],"count":manYearCount[i]});
                var womanStudyList=[];
                for(var i=0;i<womanStudy.length;i++)
                    womanStudyList.push({"name": womanStudy[i],"count":womanStudyCount[i]});
                var manStudyList=[];
                for(var i=0;i<manStudy.length;i++)
                    manStudyList.push({"name": manStudy[i],"count":manStudyCount[i]});

                var obj = { "manYear": manYearList, "womanYear": womanYearList,"manStudy": manStudyList,"womanStudy": womanStudyList};
                var myJSON = JSON.stringify(obj);
                res.end(myJSON);
                db.close();
            });
        });
    });
});


//设置头像
app.get("/update_photo", function (req, res) {
    console.log('request.url:',req.url);  //打印请求的地址
    res.setHeader("Access-Control-Allow-Origin", "*");//跨域访问
    console.log(req.query); //打印参数数据

    MongoClient.connect("mongodb://localhost", function (err, db) {
        var MyDB = db.db("movieDB");
        var whereStr = {"username":req.query.username};  // 查询条件
        var updateStr = {$set: { "photo" : req.query.photo }};//查询条件
        MyDB.collection("users").updateOne(whereStr, updateStr, function(err, result) {
            if (err) throw err;
            console.log(result);
            var obj = { value: "true", text:"头像更改成功"};
            var myJSON = JSON.stringify(obj);
            res.end(myJSON);
            db.close();
        });
    });
});

app.get("/addmovie", function (req, res) {

    res.setHeader("Access-Control-Allow-Origin", "*");//跨域访问

    MongoClient.connect("mongodb://localhost", function (err, db) {
        var MyDB = db.db("movieDB");

        MyDB.collection("moviedata", function (err, result) {
            result.findOne({movieId:req.query.movieId}, function (err, item) {
                if (!item) {
                    var insertStr = { movieId: req.query.movieId,movieTitle:req.query.movieTitle ,movieDirector:req.query.movieDirector,movieYear:req.query.movieYear,movieType:req.query.movieType,movieLanguage:req.query.movieLanguage};
                    MyDB.collection("moviedata").insertOne(insertStr,function(err, result) {
                        if (err)
                            console.log("1111");

                        console.log(req.query.movieTitle);
                        db.close();
                        var obj = { value: "true", text:"收藏成功"};
                        var myJSON = JSON.stringify(obj);
                        res.end(myJSON);});
                } else {
                    db.close();
                    var obj = { value: "false", text:"该电影已经被收藏了"};
                    var myJSON = JSON.stringify(obj);
                    res.end(myJSON);
                }
            });
        });
    });
});

function calculateTypicalUserLike(sex,year,study){
    MongoClient.connect("mongodb://localhost", function (err, db) {
        var MyDB = db.db("movieDB");
        var whereStr={"sex" :sex,"year":year,"study":study};
        MyDB.collection("users").find(whereStr).toArray(function(err,userData){
            if(err) throw err;
            var typicalUserLike = userData[0];
            for(var i=1;i<userData.length;i++){
                for(var j=0;j<userData[i].userLike.typeLike.length;j++){
                    typicalUserLike.userLike.typeLike[j].movieTypeCount=typicalUserLike.userLike.typeLike[j].movieTypeCount+userData[i].userLike.typeLike[j].movieTypeCount;
                }
                for(var j=0;j<userData[i].userLike.yearLike.length;j++){
                    typicalUserLike.userLike.yearLike[j].movieYearCount=typicalUserLike.userLike.yearLike[j].movieYearCount+userData[i].userLike.yearLike[j].movieYearCount;
                }
                for(var j=0;j<userData[i].userLike.languageLike.length;j++){
                    typicalUserLike.userLike.languageLike[j].movieLanguageCount=typicalUserLike.userLike.languageLike[j].movieLanguageCount+userData[i].userLike.languageLike[j].movieLanguageCount;
                }
            }
            for(var j=0;j<typicalUserLike.userLike.typeLike.length;j++){
                typicalUserLike.userLike.typeLike[j].movieTypeCount=typicalUserLike.userLike.typeLike[j].movieTypeCount/userData.length;
            }
            for(var j=0;j<typicalUserLike.userLike.yearLike.length;j++){
                typicalUserLike.userLike.yearLike[j].movieYearCount=typicalUserLike.userLike.yearLike[j].movieYearCount/userData.length;
            }
            for(var j=0;j<typicalUserLike.userLike.languageLike.length;j++){
                typicalUserLike.userLike.languageLike[j].movieLanguageCount=typicalUserLike.userLike.languageLike[j].movieLanguageCount/userData.length;
            }
            delete typicalUserLike._id;
            delete typicalUserLike.password;
            delete typicalUserLike.photo;
            delete typicalUserLike.username;
            console.log(typicalUserLike);
            MyDB.collection("typicaluser").findOne(whereStr,function (err,item) {
                if(!item){
                    var insertStr ={ "sex" :typicalUserLike.sex,"study":typicalUserLike.study,"year":typicalUserLike.year,"userLike":typicalUserLike.userLike  };
                    MyDB.collection("typicaluser").insertOne(insertStr,function(err, result) {
                        if (err)
                            throw err;
                        //console.log(result);
                        db.close();
                    });
                }else{
                    var updateSet = {$set: { "sex" :typicalUserLike.sex,"study":typicalUserLike.study,"year":typicalUserLike.year,"userLike":typicalUserLike.userLike  }};
                    MyDB.collection("typicaluser").updateOne(whereStr,updateSet,function(err, result) {
                        if (err)
                            throw err;
                        //console.log(result);
                        db.close();
                    });
                }
            });
        });
    });
}

function calculateUserLike(username){
    MongoClient.connect("mongodb://localhost", function (err, db) {
        var MyDB = db.db("movieDB");
        //计算用户喜好
        var i=0;
        var movieType=["剧情","爱情","奇幻","悬疑","恐怖","惊悚","动作","喜剧","科幻","古装","武侠","家庭","传记","运动","动画","音乐","犯罪","冒险","战争","歌舞","儿童","同性"];
        var movieTypeCount=[];
        for( i=0;i<movieType.length;i++){
            movieTypeCount[i]=0;
        }

        var movieLanguage=["汉语普通话","英语","日语","韩语","粤语"];
        var movieLanguageCount=[];
        for( i=0;i<movieLanguage.length;i++){
            movieLanguageCount[i]=0;
        }

        var movieYear=[{"start":1800,"end":1960},{"start":1961,"end":1980},{"start":1981,"end":1990},{"start":1991,"end":2000},{"start":2001,"end":2010},{"start":2011,"end":2015},{"start":2016,"end":2017},{"start":2018,"end":2019}];
        var movieYearCount=[];
        for( i=0;i<movieYear.length;i++){
            movieYearCount[i]=0;
        }

        var whereStr={username :username};
        MyDB.collection("collect").find(whereStr).toArray(function(err,result){
            if (err) throw err;
            console.log(result);
            if(result.length===0){//无收藏记录用户
                for( i=0;i<movieType.length;i++){
                    movieTypeCount[i]=1/movieType.length;
                }
                for( i=0;i<movieLanguage.length;i++){
                    movieLanguageCount[i]=1/movieLanguage.length;
                }
                for( i=0;i<movieYear.length;i++){
                    movieYearCount[i]=1/movieYear.length;
                }
            }
            else{//有收藏记录用户
                for(var i=0;i<result.length;i++){
                    var j=0;
                    for( j=0;j<movieType.length;j++){
                        if(result[i].movieType.indexOf(movieType[j])!== -1){
                            movieTypeCount[j]=movieTypeCount[j]+1;
                        }
                    }
                    for( j=0;j<movieLanguage.length;j++){
                        if(result[i].movieLanguage===movieLanguage[j]){
                            movieLanguageCount[j]=movieLanguageCount[j]+1;
                        }
                    }
                    var yearInt = parseInt(result[i].movieYear);
                    for( j=0;j<movieYear.length;j++){
                        if(yearInt<=movieYear[j].end&&yearInt>=movieYear[j].start){
                            movieYearCount[j]=movieYearCount[j]+1;
                        }
                    }
                }

                var totalType=0;
                for(i=0;i<movieType.length;i++) {
                    totalType=totalType+movieTypeCount[i];
                }
                for(i=0;i<movieType.length;i++) {
                    movieTypeCount[i]=movieTypeCount[i]/totalType;
                }
                for(i=0;i<movieLanguage.length;i++) {
                    movieLanguageCount[i]=movieLanguageCount[i]/result.length;
                }
                for(i=0;i<movieYear.length;i++) {
                    movieYearCount[i]=movieYearCount[i]/result.length;
                }
            }

            var userTypeLike=[];
            for(i=0;i<movieType.length;i++){
                userTypeLike.push({"movieType":movieType[i],"movieTypeCount":movieTypeCount[i]})
            }
            var userLanguageLike=[];
            for(i=0;i<movieLanguage.length;i++){
                userLanguageLike.push({"movieLanguage":movieLanguage[i],"movieLanguageCount":movieLanguageCount[i]})
            }
            var userYearLike=[];
            for(i=0;i<movieYear.length;i++){
                userYearLike.push({"movieYear":movieYear[i],"movieYearCount":movieYearCount[i]})
            }
            var userLike={"typeLike":userTypeLike,"languageLike":userLanguageLike,"yearLike":userYearLike};

            var whereStr = {"username":username};
            var updateStr = {$set: { "userLike" : userLike }};
            MyDB.collection("users").updateOne(whereStr,updateStr,function(err, result) {
                if (err) throw err;

                db.close();
            });
        });
    });
}

// json
app.post("/json", function(req, res){
	console.log(req.body)
});

// text text
app.post("/text", function(req, res){
	console.log(req.body)
});

// create server listening at 3000
var server = app.listen(3500, function(){
	console.log("App listening at 3500...")
});
