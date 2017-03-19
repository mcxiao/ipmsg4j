
IPMsg4J
=======

About
-----
IPMsg4J is a Java implementation of [IP Messenger]  (A Send/Receive message service using the TCP/UDP Port).

Getting started
---------------
```java

IPMsgConfiguration config = IPMsgConfiguration.create().setLocalHost(localHost);
AbstractConnection connection = new IPMsgConnectionImpl(config);
connection.connect();
// TODO Use connection
```

Wiki
----
The IP Messenger protocol see [the website](https://ipmsg.org/index.html.en) or [中文版在这里](https://github.com/mcxiao/wiki_zhCN/tree/master/ipmsg).

Download
--------
coming soon

License
-------

```
Copyright [2017] [xiao]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[IP Messenger]: https://ipmsg.org/index.html.en