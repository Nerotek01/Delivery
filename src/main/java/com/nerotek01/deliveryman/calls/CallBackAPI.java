package com.nerotek01.deliveryman.calls;

public interface CallBackAPI<Reply> {
  void done(Reply paramReply);
}