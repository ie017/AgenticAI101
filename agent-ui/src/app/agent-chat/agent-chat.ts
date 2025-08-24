import { Component } from '@angular/core';
import {HttpClient, HttpDownloadProgressEvent, HttpEventType} from '@angular/common/http';

@Component({
  selector: 'app-agent-chat',
  standalone: false,
  templateUrl: './agent-chat.html',
  styleUrl: './agent-chat.css'
})
export class AgentChat {
  query : string ="";
  response : any ;
  progress : boolean = false;
  constructor(private http : HttpClient) {
  }

  askAgent() {
    this.response="";
    this.progress=true;
    this.http.get("http://localhost:9090/agent/chat?query="+this.query,
      {responseType:'text', observe : 'events', reportProgress : true})
      .subscribe({
        next:evt => {
          if( evt.type === HttpEventType.DownloadProgress){
            this.response =  (evt as HttpDownloadProgressEvent).partialText
          }
        },
        error : err => {
          this.progress = false;
        },
        complete :() => {
          this.progress = false;
        }
      })
  }
}
