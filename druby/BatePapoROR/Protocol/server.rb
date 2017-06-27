require 'drb'
require_relative '../Application/controller.rb'

class Server
  def initialize(args={})
    @controller = Controller.new(:favoring_readers)
  end

  def define_strategy(strategy)
    initialize(strategy)
  end

  def read(filepath)
    @controller.read filepath
  end

  def write(filepath, value)
    @controller.write(filepath, value)
  end
end

DRb.start_service('druby://localhost:61676', Server.new)
puts "Server is ready!"
DRb.thread.join
