<?php

$command = $_POST['command'];

if ($command == "push") {
    $message = $_POST['message'];
    $account = $_POST['account'];
    $filename = hash('ripemd160', $account);
    
    while(file_exists($filename.".lock")){} //ждем пока блокировка
    fclose(fopen($filename.".lock", "w"));  //создаем блокироовку
    
    //чтение
    $data = file_get_contents($filename);
    $allMessages = unserialize($data);
    //добавление
    $allMessages[] = $message;
    
    //запись
    $data = serialize($allMessages);
    file_put_contents($filename, $data);
    
    unlink($filename.".lock"); // разблокировка
} elseif ($command == "pull") {
    $account = $_POST['account'];
    $filename = hash('ripemd160', $account);
    
    while(file_exists($filename.".lock")){} //ждем пока блокировка
    fclose(fopen($filename.".lock", "w"));  //создаем блокироовку
    
    //чтение
    $data = file_get_contents($filename);
    $allMessages = unserialize($data);
    
    $json = json_encode($allMessages);
    
    //удаление
    unlink($filename);
    
    //вывод
    if ($json != "false")
        echo $json;
    else
        echo "[]";
    
    unlink($filename.".lock"); // разблокировка
} elseif ($command == "check") {
    $account = $_POST['account'];
    $filename = hash('ripemd160', $account);
    
    while(file_exists($filename.".lock")){} //ждем пока блокировка
    fclose(fopen($filename.".lock", "w"));  //создаем блокироовку
    
    if(file_exists($filename))
        echo "true";
    else
        echo "false";
        
    unlink($filename.".lock"); // разблокировка
} else {
    header("Location: https://github.com/RazuvaevDD/I2PSecChat/", true, 301);
    exit();
}


?>