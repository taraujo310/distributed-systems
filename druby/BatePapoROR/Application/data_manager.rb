require_relative 'wlocker.rb'
require_relative 'rlocker.rb'

class DataManager
  class NoNameForFileError < StandardError; end

  def initialize(args)
    @@base ||= {}
    @@base[args[:filepath]] = locker_factory(args[:strategy], args[:filepath])
    @baseMutex = Mutex.new
  end

  def read(filepath, id)
    locker = nil
    @baseMutex.synchronize { locker = get_locker_with filepath }

    message = ""
    locker.access_read do |resource|
      message = resource.read id
    end
    message
  end

  def write(filepath, message, id)
    locker = nil
    @baseMutex.synchronize { locker = get_locker_with filepath }

    locker.access_write do |resource|
      resource.write(message, id)
    end
  end

  private
  def get_locker_with(filepath)
    @@base[filepath]
  end

  def locker_factory(strategy, filepath)
    case strategy
      when :favoring_readers
        RLocker.new(filepath)
      when :favoring_writers
        WLocker.new(filepath)
      when :no_favoring
        NLocker.new(filepath)
      else
        NLocker.new(filepath)
    end
  end
end
