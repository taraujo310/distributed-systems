require 'drb'

class HelloWorldServer
  def say
    'Hello World in Ruby!'
  end
end

DRb.start_service('druby://localhost:61676', HelloWorldServer.new)
DRb.thread.join
