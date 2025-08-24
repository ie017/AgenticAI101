import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { AgentChat } from './agent-chat/agent-chat';
import {FormsModule} from "@angular/forms";
import {MarkdownComponent, provideMarkdown} from 'ngx-markdown';
import {provideHttpClient} from '@angular/common/http';

@NgModule({
  declarations: [
    App,
    AgentChat
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    MarkdownComponent
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideHttpClient(), provideMarkdown()
  ],
  bootstrap: [App]
})
export class AppModule { }
